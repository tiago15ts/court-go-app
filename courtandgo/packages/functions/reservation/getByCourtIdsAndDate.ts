import { getReservationsByCourtIdsAndDate } from "../../core/queries/reservation";

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");

  const { courtIds, date } = body;
  const reservations = await getReservationsByCourtIdsAndDate(courtIds, date);

  return {
    statusCode: 200,
    body: JSON.stringify(reservations),
  };
}
