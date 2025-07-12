import { getClubsByDistrict } from "../../core/queries/club";

export async function handler(event) {
  const { district } = event.queryStringParameters || {};
  const clubs = await getClubsByDistrict(district);
  return {
    statusCode: 200,
    body: JSON.stringify(clubs),
  };
}
