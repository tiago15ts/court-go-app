import { db } from "../db";

export async function getWeeklySchedulesForCourt(courtId: number) {
  const res = await db.query(
    "SELECT * FROM WeeklySchedule WHERE courtId = $1",
    [courtId]
  );
  return res.rows;
}

export async function getSpecialSchedulesForCourt(courtId: number) {
  const res = await db.query(
    "SELECT * FROM SpecialSchedule WHERE courtId = $1",
    [courtId]
  );
  return res.rows;
}

export async function getWeeklyScheduleById(scheduleId: number) {
  const res = await db.query(
    "SELECT * FROM WeeklySchedule WHERE scheduleId = $1",
    [scheduleId]
  );
  return res.rows[0] || null;
}

export async function getSpecialScheduleById(scheduleId: number) {
  const res = await db.query(
    "SELECT * FROM SpecialSchedule WHERE scheduleId = $1",
    [scheduleId]
  );
  return res.rows[0] || null;
}

export async function createWeeklySchedule(data: {
  courtId: number;
  dayOfWeek: number;
  startTime: string;
  endTime: string;
}) {
  const res = await db.query(
    `INSERT INTO WeeklySchedule (courtId, dayOfWeek, startTime, endTime)
     VALUES ($1, $2, $3, $4)
     RETURNING *`,
    [data.courtId, data.dayOfWeek, data.startTime, data.endTime]
  );
  return res.rows[0];
}

export async function createSpecialSchedule(data: {
  courtId: number;
  date: string;
  startTime: string;
  endTime: string;
}) {
  const res = await db.query(
    `INSERT INTO SpecialSchedule (courtId, date, startTime, endTime)
     VALUES ($1, $2, $3, $4)
     RETURNING *`,
    [data.courtId, data.date, data.startTime, data.endTime]
  );
  return res.rows[0];
}