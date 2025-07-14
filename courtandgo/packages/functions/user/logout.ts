import { CognitoIdentityProviderClient, GlobalSignOutCommand } from "@aws-sdk/client-cognito-identity-provider";

const client = new CognitoIdentityProviderClient({ region: "eu-west-3" });

export async function handler(event) {
  const token = event.headers?.Authorization?.replace("Bearer ", "");

  if (!token) {
    return { statusCode: 401, body: "Token inválido ou ausente." };
  }

  try {
    await client.send(new GlobalSignOutCommand({ AccessToken: token }));

    return {
      statusCode: 200,
      body: JSON.stringify({ message: "Logout efetuado com sucesso." }),
    };
  } catch (e) {
    return {
      statusCode: 500,
        headers: {
    "Content-Type": "application/json"
  },
      body: JSON.stringify({ error: "Erro ao efetuar logout." }),
    };
  }
}
