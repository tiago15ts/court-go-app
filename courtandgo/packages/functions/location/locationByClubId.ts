import { getLocationsByClubId } from "../../core/queries/location";

export async function getLocationsByClubIdHandler(event: any) {
  const clubId = parseInt(event.pathParameters.clubId);
  try {
    const locations = await getLocationsByClubId(clubId);
    return {
      statusCode: 200,
      body: JSON.stringify(locations),
    };
  } catch (error) {
    console.error("Error fetching locations:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ message: "Failed to fetch locations" }),
    };
  }
}
