import { getCourtsBySport } from "../../core/queries/court";

export async function handler(event) {
  const { sport } = event.pathParameters || {};
  const courts = await getCourtsBySport(sport);
  return {
    statusCode: 200,
    body: JSON.stringify(courts),
  };
}
