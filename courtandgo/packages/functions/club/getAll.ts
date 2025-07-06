import { getAllClubs } from "../../core/queries/club";

export async function handler() {
  const clubs = await getAllClubs();
  return {
    statusCode: 200,
    body: JSON.stringify(clubs),
  };
}
