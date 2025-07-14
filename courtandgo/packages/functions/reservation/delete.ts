import { deleteReservation } from "../../core/queries/reservation";

export async function handler(event) {
  const { id } = event.pathParameters || {};
  const success = await deleteReservation(Number(id));

  return {
    statusCode: success ? 200 : 404,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ success }),
  };
}
