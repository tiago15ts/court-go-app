import { updateLocation } from "../../core/queries/location";

export async function updateLocationHandler(event: any) {
  const locationId = parseInt(event.pathParameters.locationId);
  const locationData = JSON.parse(event.body);

  try {
    const updatedLocation = await updateLocation({ locationId, ...locationData });
    return {
      statusCode: 200,
      body: JSON.stringify(updatedLocation),
    };
  } catch (error) {
    console.error("Error updating location:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ message: "Failed to update location" }),
    };
  }
}
