import { getAllReservations } from "../../core/queries/reservation";

export async function handler() {
  const reservations = await getAllReservations();
  return {
    statusCode: 200,
    body: JSON.stringify(reservations),
  };
}
