import { getLocationByClubId } from "../../core/queries/location";

export async function handler(event: any) {
  const clubId = parseInt(event.pathParameters.clubId);
  try {
    const location = await getLocationByClubId(clubId);
    return {
      statusCode: 200,
      body: JSON.stringify(location),
    };
  } catch (error) {
    console.error("Error fetching location:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ message: "Failed to fetch location" }),
    };
  }
}
