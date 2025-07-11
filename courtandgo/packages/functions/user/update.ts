import {
  CognitoIdentityProviderClient,
  UpdateUserAttributesCommand,
  GetUserCommand,
} from "@aws-sdk/client-cognito-identity-provider";

import { updateUser } from "../../core/queries/user";


const client = new CognitoIdentityProviderClient({ region: "eu-west-3" });

export async function handler(event) {
  const token = event.headers?.Authorization?.replace("Bearer ", "");
  if (!token) {
    return { statusCode: 401, body: JSON.stringify({ error: "Token inválido ou ausente." }) };
  }

  const body = JSON.parse(event.body || "{}");

  const {
    name,
    phone,
    countryId,
    birthdate,
    weight,
    height,
    gender,
    playerId,
  } = body;

  try {
    // 1. Atualizar atributos no Cognito (apenas os suportados por Cognito)
    const cognitoAttributes: { Name: string; Value: string }[] = [];

    if (name) {
      cognitoAttributes.push({ Name: "name", Value: String(name) });
    }
    if (phone) {
      cognitoAttributes.push({ Name: "phone_number", Value: String(phone) });
    }


    if (cognitoAttributes.length > 0) {
      await client.send(
        new UpdateUserAttributesCommand({
          AccessToken: token,
          UserAttributes: cognitoAttributes,
        })
      );
    }

    // 2. Atualizar dados na BD local
    const updatedUser = await updateUser({
      name,
      phone,
      countryId,
      birthdate,
      weight,
      height,
      gender,
      playerId,
    });

    return {
      statusCode: 200,
      body: JSON.stringify({
        message: "Perfil atualizado com sucesso.",
        user: updatedUser,
      }),
    };
  } catch (e) {
    console.error("Erro ao atualizar perfil:", e);
    return {
      statusCode: 500,
      body: JSON.stringify({ error: "Erro ao atualizar perfil." }),
    };
  }
}
