import { getClubIdByCourtId } from "../../core/queries/club";

export async function handler(event) {
  const { courtId } = event.pathParameters || {};
  const clubId = await getClubIdByCourtId(Number(courtId));
  return {
    statusCode: 200,
    body: JSON.stringify({ clubId }),
  };
}
