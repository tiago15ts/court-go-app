import { getWeeklySchedulesForCourt } from "../../core/queries/schedule";

export async function handler(event) {
  const { courtId } = event.pathParameters || {};

  const schedules = await getWeeklySchedulesForCourt(Number(courtId));
  return {
    statusCode: 200,
    body: JSON.stringify(schedules),
  };
}
