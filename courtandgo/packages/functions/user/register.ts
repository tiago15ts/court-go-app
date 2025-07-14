import {
  CognitoIdentityProviderClient,
  SignUpCommand,
  InitiateAuthCommand,
  AdminConfirmSignUpCommand,
  AdminCreateUserCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { registerUser } from "../../core/queries/user";

const COGNITO_USER_POOL_ID = "eu-west-3_WHT90YCJ3";
const COGNITO_CLIENT_ID = "1jqtv9mvvpv0fjfe0o2dbhallc";
const AWS_REGION = "eu-west-3";

const cognito = new CognitoIdentityProviderClient({ region: AWS_REGION });

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");
  const { email, password, name, countryCode, contact } = body;

  if (!email || !password || !name || !contact || !countryCode) {
    return {
      statusCode: 400,
      body: JSON.stringify({ error: "Campos obrigatórios em falta." }),
    };
  }

  try {
    
    
    const command = new SignUpCommand({
      ClientId: COGNITO_CLIENT_ID!,
      Username: email,
      Password: password,
      UserAttributes: [
        { Name: "name", Value: name },
        { Name: "phone_number", Value: `${countryCode}${contact}` },
        { Name: "email", Value: email },
      ],
    });

    await cognito.send(command);
    

    /*
    await cognito.send(new AdminConfirmSignUpCommand({
      UserPoolId: COGNITO_USER_POOL_ID,
      Username: email,
    }));
    */  
    
/*
//isto nao funciona porque a confirmacao do utilizador tem de ser feita manualmente
    const authResponse = await cognito.send(
      new InitiateAuthCommand({
        AuthFlow: "USER_PASSWORD_AUTH",
        ClientId: COGNITO_CLIENT_ID,
        AuthParameters: {
          USERNAME: email,
          PASSWORD: password,
        },
      })
    );

    const tokens = {
      accessToken: authResponse.AuthenticationResult?.AccessToken,
      idToken: authResponse.AuthenticationResult?.IdToken,
      refreshToken: authResponse.AuthenticationResult?.RefreshToken,
    };
    */

    const user = await registerUser({
      email: email,
      name: name,
      countryId: countryCode,
      phone: contact,
    });

    return {
  statusCode: 200,
  headers: {
    "Content-Type": "application/json"
  },
  body: JSON.stringify(user),
};

  } catch (err) {
     console.error("Erro no handler:", err);
    return {
      statusCode: 400,
      body: JSON.stringify({ error: err.message }),
    };
  }
}
