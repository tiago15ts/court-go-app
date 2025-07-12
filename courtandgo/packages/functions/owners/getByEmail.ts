import { getOwnerByEmail } from "../../core/queries/owner";

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");
  const { email } = body;

  try {
    const owner = await getOwnerByEmail(email);

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
