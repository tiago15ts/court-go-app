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

  if (!id) {
    return { statusCode: 400, body: JSON.stringify({ error: "ID do utilizador é obrigatório." }) };
  }

  try {
    // 1. Atualizar atributos no Cognito (apenas os suportados por Cognito)
    const cognitoAttributes: { Name: string; Value: string }[] = [];

    if (body.name) {
      cognitoAttributes.push({ Name: "name", Value: String(body.name) });
    }
    if (body.phone) {
      cognitoAttributes.push({ Name: "phone_number", Value: String(body.countryCode + body.phone) });
    }

    if (cognitoAttributes.length > 0) {
      await client.send(
        new UpdateUserAttributesCommand({
          AccessToken: token,
          UserAttributes: cognitoAttributes,
        })
      );
    }
    console.log("code: ", body.countryCode);

    // 2. Atualizar dados na BD local
    const updatedUser = await updateUser({
      id: id,
      email: body.email,
      name: body.name,
      countryCode: body.countryCode,
      phone: body.phone,
      gender: body.gender,
      birthdate: body.birthdate,
      weight: body.weight,
      height: body.height,
    });


    return {
      statusCode: 200,
        headers: {
    "Content-Type": "application/json"
  },
      body: JSON.stringify(updatedUser),
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
