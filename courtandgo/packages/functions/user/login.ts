import {
  CognitoIdentityProviderClient,
  InitiateAuthCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { getUserByEmail } from "../../core/queries/user";

const COGNITO_USER_POOL_ID = "eu-west-3_6c1AJ3Oez";
const COGNITO_CLIENT_ID = "1fh5i2j79qsdbqihk2lmo0q6q6";
const AWS_REGION = "eu-west-3";

const client = new CognitoIdentityProviderClient({ region: AWS_REGION });

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");
  const { email, password } = body;

  try {
    const command = new InitiateAuthCommand({
      AuthFlow: "USER_PASSWORD_AUTH",
      ClientId: COGNITO_CLIENT_ID!,
      AuthParameters: {
        USERNAME: email,
        PASSWORD: password,
      },
    });

    const response = await client.send(command);
    
    const tokens = {
      accessToken: response.AuthenticationResult?.AccessToken,
      idToken: response.AuthenticationResult?.IdToken,
      refreshToken: response.AuthenticationResult?.RefreshToken,
    };

    const user = await getUserByEmail(email);

    console.log(tokens);

    return {
      statusCode: 200,
      body: JSON.stringify({
        user
      }),
    };
  } catch (err) {
    console.error("Erro login:", err);
    return {
      statusCode: 401,
      body: JSON.stringify({ error: "Credenciais inválidas" }),
    };
  }
}
