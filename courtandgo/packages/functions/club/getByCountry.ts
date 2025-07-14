import { getClubsByCountry } from "../../core/queries/club";

export async function handler(event) {
  const { country } = event.queryStringParameters || {};
  const clubs = await getClubsByCountry(country);
  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clubs),
  };
}
