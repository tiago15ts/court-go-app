import { getCourtsFiltered } from "../../core/queries/court";

export async function handler(event) {
  const qs = event.queryStringParameters || {};

  const courts = await getCourtsFiltered(qs.district || "", qs.sport || "");
  return {
    statusCode: 200,
    body: JSON.stringify(courts),
  };
}
