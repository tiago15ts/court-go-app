import { getReservationById } from "../../core/queries/reservation";

export async function handler(event) {
  const { id } = event.pathParameters || {};
  const reservation = await getReservationById(Number(id));

  return {
    statusCode: reservation ? 200 : 404,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(reservation || { error: "Reservation not found" }),
  };
}
