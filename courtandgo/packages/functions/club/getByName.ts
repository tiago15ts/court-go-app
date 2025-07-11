import { getClubsByName } from "../../core/queries/club";

export async function handler(event) {
  const { name } = event.queryStringParameters || {};
  const clubs = await getClubsByName(name);
  return {
    statusCode: 200,
    body: JSON.stringify(clubs),
  };
}
