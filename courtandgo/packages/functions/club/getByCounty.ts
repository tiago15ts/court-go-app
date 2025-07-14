import { getClubsByCounty } from "../../core/queries/club";

export async function handler(event) {
  const { county } = event.queryStringParameters || {};
  const clubs = await getClubsByCounty(county);
  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clubs),
  };
}
