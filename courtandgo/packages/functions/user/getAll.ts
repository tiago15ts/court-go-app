import { getAllUsers } from "../../core/queries/user";

export async function handler(event) {
  const users = await getAllUsers();

  return {
    statusCode: 200,
      headers: {
    "Content-Type": "application/json"
  },
    body: JSON.stringify(users),
  };
}
