import { getUserByEmail } from "../../core/queries/user";

export async function handler(event) {
  const { email } = event.pathParameters || {};
  const user = await getUserByEmail(email);

  return {
    statusCode: user ? 200 : 404,
    body: JSON.stringify(user || { error: "User by email not found" }),
  };
}
