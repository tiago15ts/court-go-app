import { updateUser } from "../../core/queries/user";

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");
  const user = await updateUser(body);

  return {
    statusCode: 200,
    body: JSON.stringify(user),
  };
}
