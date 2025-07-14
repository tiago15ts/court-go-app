import { getClubById } from "../../core/queries/club";

export async function handler(event) {
  const { id } = event.pathParameters || {};
  const club = await getClubById(Number(id));
  return {
    statusCode: club ? 200 : 404,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(club || { error: "Club not found" }),
  };
}
