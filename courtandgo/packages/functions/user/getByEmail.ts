import { getUserByEmail } from "../../core/queries/user";

export async function handler(event) {
  const { email } = event.queryStringParameters || {};

  if (!email) {
    return {
      statusCode: 400,
      body: JSON.stringify({ error: "Missing 'email' query parameter" }),
    };
  }

  const user = await getUserByEmail(email);

  return {
    statusCode: user ? 200 : 404,
      headers: {
    "Content-Type": "application/json"
  },
    body: JSON.stringify(user || { error: "User by email not found" }),
  };
}
