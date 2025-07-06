import { createSpecialSchedule } from "../../core/queries/schedule";

export async function handler(event) {
  const body = JSON.parse(event.body || "{}");

  const created = await createSpecialSchedule(body);

  return {
    statusCode: 201,
    body: JSON.stringify(created),
  };
}
