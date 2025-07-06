import { getClubsByOwnerId } from "../../core/queries/club";

export async function handler(event) {
  const { ownerId } = event.pathParameters || {};
  const clubs = await getClubsByOwnerId(Number(ownerId));
  return {
    statusCode: 200,
    body: JSON.stringify(clubs),
  };
}
