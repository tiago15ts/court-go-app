import { getClubsByPostalCode } from "../../core/queries/club";

export async function handler(event) {
  const { postalCode } = event.queryStringParameters || {};
  const clubs = await getClubsByPostalCode(postalCode);
  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clubs),
  };
}
