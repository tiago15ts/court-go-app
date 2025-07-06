// frontend/src/api/schedule.ts
import { VITE_API_URL } from '../config';

export async function getWeeklySchedules(courtId: number) {
  const res = await fetch(`${VITE_API_URL}/schedule/weekly/${courtId}`);
  return await res.json();
}

export async function createWeeklySchedule(schedule: any) {
  const res = await fetch(`${VITE_API_URL}/schedule/weekly`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(schedule),
  });
  return await res.json();
}

export async function createSpecialSchedule(schedule: any) {
  const res = await fetch(`${VITE_API_URL}/schedule/special`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(schedule),
  });
  return await res.json();
}

export async function getSpecialSchedules(courtId: number) {
  const res = await fetch(`${VITE_API_URL}/schedule/special/${courtId}`);
  return await res.json();
}