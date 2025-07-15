import { getClubIdByCourtId } from "../../core/queries/club";

export async function handler(event) {
  const { courtId } = event.pathParameters || {};
  const clubId = await getClubIdByCourtId(Number(courtId));
  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clubId),
  };
}
