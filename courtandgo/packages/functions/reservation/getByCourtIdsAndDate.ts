import { getReservationsByCourtIdsAndDate } from "../../core/queries/reservation";

export async function handler(event) {
  const qs = event.queryStringParameters || {};

  // courtIds chega como string "1,2,3"
  const courtIds = (qs.courtIds || "")
    .split(",")
    .map(id => parseInt(id))
    .filter(id => !isNaN(id));

  const date = qs.date;

  // Validação opcional
  if (!courtIds.length || !date) {
    return {
      statusCode: 400,
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ message: "Missing or invalid courtIds or date" }),
    };
  }

  const reservations = await getReservationsByCourtIdsAndDate(courtIds, date);

  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(reservations),
  };
}