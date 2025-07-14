import { getAllClubs } from "../../core/queries/club";

export async function handler() {
  const clubs = await getAllClubs();
  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clubs),
  };
}
