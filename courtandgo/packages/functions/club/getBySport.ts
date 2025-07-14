import { getClubsBySport } from "../../core/queries/club";

export async function handler(event) {
  const { sport } = event.queryStringParameters || {};
  const clubs = await getClubsBySport(sport);
  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clubs),
  };
}
