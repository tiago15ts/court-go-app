import { getCourtsByOwnerId } from "../../core/queries/court";

export async function handler(event) {
  const { ownerId } = event.pathParameters || {};
  const courts = await getCourtsByOwnerId(Number(ownerId));

  return {
    statusCode: 200,
    body: JSON.stringify(courts),
  };
}
