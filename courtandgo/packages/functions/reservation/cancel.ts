import { cancelReservation } from "../../core/queries/reservation";

export async function handler(event: any) {
  const { id } = event.pathParameters;
  const success = await cancelReservation(Number(id));

  return {
    statusCode: success ? 200 : 404,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ success }),
  };
}
