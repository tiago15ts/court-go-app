import { getCourtsByDistrict } from "../../core/queries/court";

export async function handler(event) {
  const { district } = event.queryStringParameters || {};
  const courts = await getCourtsByDistrict(district);
  return {
    statusCode: 200,
    body: JSON.stringify(courts),
  };
}
