import {
  CognitoIdentityProviderClient,
  SignUpCommand,
  AdminConfirmSignUpCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { createOwner } from "../../core/queries/owner";

const REGION = "eu-west-3";
const USER_POOL_ID = "eu-west-3_6c1AJ3Oez";
const CLIENT_ID = "1fh5i2j79qsdbqihk2lmo0q6q6";

const cognito = new CognitoIdentityProviderClient({ region: REGION });

export async function handler(event: any) {
  const body = JSON.parse(event.body || "{}");
  const { email, password, name, phone } = body;

  if (!email || !password || !name || !phone) {
    return {
      statusCode: 400,
      body: JSON.stringify({ error: "Campos obrigatórios em falta." }),
    };
  }

  try {
    
    await cognito.send(
      new SignUpCommand({
        ClientId: CLIENT_ID,
        Username: email,
        Password: password,
        UserAttributes: [
          { Name: "email", Value: email },
          { Name: "name", Value: name },
          { Name: "phone_number", Value: phone },
        ],
      })
    );



    await cognito.send(new AdminConfirmSignUpCommand({
      UserPoolId: USER_POOL_ID,
      Username: email,
    }));

    
    
    

    const owner = await createOwner({
      email,
      name,
      phone,
    });

    return {
      statusCode: 201,
      body: JSON.stringify({ owner, message: "Owner registado com sucesso." }),
    };
  } catch (err: any) {
    console.error("Erro no registo do owner:", err);
    return {
      statusCode: 400,
      body: JSON.stringify({ error: err.message }),
    };
  }
}
