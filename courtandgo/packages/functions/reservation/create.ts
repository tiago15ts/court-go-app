import { createReservation } from "../../core/queries/reservation";

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");

  const reservation = await createReservation(body);
  return {
    statusCode: 201,
    body: JSON.stringify(reservation),
  };
}
