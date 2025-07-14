import { getReservationsForPlayer } from "../../core/queries/reservation";

export async function handler(event) {
  const { playerId } = event.pathParameters || {};
  const reservations = await getReservationsForPlayer(Number(playerId));

  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(reservations),
  };
}
