import { createCourt } from "../../core/queries/court";

export async function handler(event: any) {
  try {
    const courtData = JSON.parse(event.body);
    const newCourt = await createCourt(courtData);
    
    return {
      statusCode: 201,
      body: JSON.stringify(newCourt),
    };
  } catch (error) {
    console.error("Error creating court:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ message: "Failed to create court" }),
    };
  }
}