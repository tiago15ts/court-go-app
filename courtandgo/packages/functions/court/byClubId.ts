
import { getCourtsByClub } from "../../core/queries/court";

export async function handler(event) {
  const { clubId } = event.pathParameters || {};
  const courts = await getCourtsByClub(Number(clubId));

  return {
    statusCode: 200,
    body: JSON.stringify(courts),
  };
}
