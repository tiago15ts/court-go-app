import { updateClub } from "../../core/queries/club";

export async function handler(event: any) {
  const clubData = JSON.parse(event.body);
  const updatedClub = await updateClub(clubData);
  return {
    statusCode: 200,
    body: JSON.stringify(updatedClub),
  };
}
