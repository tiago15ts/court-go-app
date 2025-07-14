import { getCourtsBySport as getCourtsBySportType } from "../../core/queries/court";

export async function handler(event) {
  const { sport } = event.queryStringParameters || {};
  const courts = await getCourtsBySportType(sport);
  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(courts),
  };
}
