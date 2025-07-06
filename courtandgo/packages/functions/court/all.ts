import { getAllCourts } from "../../core/queries/court";

export async function handler() {
  const courts = await getAllCourts();
  return {
    statusCode: 200,
    body: JSON.stringify(courts),
  };
}
