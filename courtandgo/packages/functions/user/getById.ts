import { getUserById } from "../../core/queries/user";

export async function handler(event) {
  const { id } = event.pathParameters || {};
  const user = await getUserById(Number(id));

  return {
    statusCode: user ? 200 : 404,
      headers: {
    "Content-Type": "application/json"
  },
    body: JSON.stringify(user || { error: "User not found" }),
  };
}
