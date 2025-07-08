import { createLocation } from "../../core/queries/location";

export async function createLocationHandler(event: any) {
  try {
    const locationData = JSON.parse(event.body);
    const newLocation = await createLocation(locationData);

    return {
      statusCode: 201,
      body: JSON.stringify(newLocation),
    };
  } catch (error) {
    console.error("Error creating location:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ message: "Failed to create location" }),
    };
  }

}
