import { createClub } from "../../core/queries/club";

export async function handler(event: any) {
  const clubData = JSON.parse(event.body);
  const newClub = await createClub(clubData);
  return {
    statusCode: 201,
    body: JSON.stringify(newClub),
  };
}
