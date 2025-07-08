import { updateCourt } from "../../core/queries/court";

export async function handler(event: any) {
  try {
    const courtData = JSON.parse(event.body);
    const updatedCourt = await updateCourt(courtData);

    return {
      statusCode: 200,
      body: JSON.stringify(updatedCourt),
    };
  } catch (error) {
    console.error("Error updating court:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ message: "Failed to update court" }),
    };
  }
}
