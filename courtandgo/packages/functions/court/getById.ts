import { getCourtById } from "../../core/queries/court";

export async function handler(event) {
  const { id } = event.pathParameters || {};
  const court = await getCourtById(Number(id));

  return {
    statusCode: court ? 200 : 404,
    body: JSON.stringify(court || { error: "Court not found" }),
  };
}
