import { confirmReservation } from "../../core/queries/reservation";

export async function handler(event) {
  const { id } = event.pathParameters || {};
  const success = await confirmReservation(Number(id));

  return {
    statusCode: success ? 200 : 404,
    body: JSON.stringify({ success }),
  };
}
