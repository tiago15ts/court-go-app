import { updateReservation } from "../../core/queries/reservation";

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");

  const updated = await updateReservation(body);
  return {
    statusCode: updated ? 200 : 404,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(updated || { error: "Reservation not found" }),
  };
}
