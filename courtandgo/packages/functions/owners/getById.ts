import { getOwnerById } from "../../core/queries/owner";

//no route defined
export async function handler(event) {
  const { id } = event.pathParameters || {};

  if (!id) {
    return {
      statusCode: 400,
      body: JSON.stringify({ error: "ID is required" }),
    };
  }

  try {
    const owner = await getOwnerById(Number(id));

    if (!owner) {
      return {
        statusCode: 404,
        body: JSON.stringify({ error: "Owner not found" }),
      };
    }

    return {
      statusCode: 200,
      body: JSON.stringify({ owner }),
    };
  } catch (err) {
    console.error("Error fetching owner:", err);
    return {
      statusCode: 500,
      body: JSON.stringify({ error: "Internal server error" }),
    };
  }
}
