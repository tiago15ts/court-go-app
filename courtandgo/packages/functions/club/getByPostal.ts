import { getClubsByPostalCode } from "../../core/queries/club";

export async function handler(event) {
  const { postalCode } = event.pathParameters || {};
  const clubs = await getClubsByPostalCode(postalCode);
  return {
    statusCode: 200,
    body: JSON.stringify(clubs),
  };
}
