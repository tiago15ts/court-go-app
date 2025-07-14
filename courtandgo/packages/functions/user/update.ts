import {
  CognitoIdentityProviderClient,
  UpdateUserAttributesCommand,
  GetUserCommand,
} from "@aws-sdk/client-cognito-identity-provider";

import { updateUser } from "../../core/queries/user";


const client = new CognitoIdentityProviderClient({ region: "eu-west-3" });

export async function handler(event) {
  const authHeader = event.headers?.authorization || event.headers?.Authorization;
  if (!authHeader?.startsWith("Bearer ")) {
    return { statusCode: 401, body: "Missing or malformed token" };
  }
  const token = authHeader.replace("Bearer ", "");

  if (!token) {
    return { statusCode: 401, body: JSON.stringify({ error: "Token inválido ou ausente." }) };
  }

  const body = JSON.parse(event.body || "{}");

  const { id } = event.pathParameters || {};

  const {
    name,
    phone,
    countryId,
    birthdate,
    weight,
    height,
    gender,
  } = body;

  try {
    // 1. Atualizar atributos no Cognito (apenas os suportados por Cognito)
    const cognitoAttributes: { Name: string; Value: string }[] = [];

    if (name) {
      cognitoAttributes.push({ Name: "name", Value: String(name) });
    }
    if (phone) {
      cognitoAttributes.push({ Name: "phone_number", Value: String(countryId + phone) });
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
      playerId: id,
    });

    return {
      statusCode: 200,
        headers: {
    "Content-Type": "application/json"
  },
      body: JSON.stringify({updatedUser}),
    };
  } catch (e) {
    console.error("Erro ao atualizar perfil:", e);
    return {
      statusCode: 500,
        headers: {
    "Content-Type": "application/json"
  },
      body: JSON.stringify({ error: "Erro ao atualizar perfil." }),
    };
  }
}
