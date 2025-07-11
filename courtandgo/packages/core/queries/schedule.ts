import { db } from "../db";
import { mapRowToSpecialScheduleDTO } from "../mappers/specialScheduleMapper";
import { mapRowToWeeklyScheduleDTO } from "../mappers/weeklyScheduleMapper";

export async function getWeeklySchedulesForCourt(courtId: number) {
  const client = await db.connect();
  const res = await client.query(
    "SELECT * FROM WeeklySchedule WHERE courtId = $1",
    [courtId]
  );
  client.release();
  return res.rows.map(mapRowToWeeklyScheduleDTO);
}

export async function getSpecialSchedulesForCourt(courtId: number) {
  const client = await db.connect();
  const res = await client.query(
    "SELECT * FROM SpecialSchedule WHERE courtId = $1",
    [courtId]
  );
  client.release();
  return res.rows.map(mapRowToSpecialScheduleDTO);
}

export async function getWeeklyScheduleById(scheduleId: number) {
  const client = await db.connect();
  const res = await client.query(
    "SELECT * FROM WeeklySchedule WHERE scheduleId = $1",
    [scheduleId]
  );
  client.release();
  return res.rows.length > 0 ? mapRowToWeeklyScheduleDTO(res.rows[0]) : null;
}

export async function getSpecialScheduleById(scheduleId: number) {
  const client = await db.connect();
  const res = await client.query(
    "SELECT * FROM SpecialSchedule WHERE scheduleId = $1",
    [scheduleId]
  );
  client.release();
  return res.rows.length > 0 ? mapRowToSpecialScheduleDTO(res.rows[0]) : null;
}

//funcoes exclusivas de admin
export async function createWeeklySchedule(data: {
  courtId: number;
  dayOfWeek: string; // 'Monday', 'Tuesday', etc.
  startTime: string;
  endTime: string;
}) {
  const client = await db.connect();
  const res = await client.query(
    `INSERT INTO WeeklySchedule (courtId, dayOfWeek, startTime, endTime)
     VALUES ($1, $2, $3, $4)
     RETURNING *`,
    [data.courtId, data.dayOfWeek, data.startTime, data.endTime]
  );
  client.release();
  return res.rows[0];
}

export async function createSpecialSchedule(data: {
  courtId: number;
  date: string; // yyyy-mm-dd format
  startTime: string;
  endTime: string;
  working: boolean; // true = aberto nesse dia, false = fechado (feriado)
}) {
  const client = await db.connect();
  const res = await client.query(
    `INSERT INTO SpecialSchedule (courtId, date, startTime, endTime, working)
     VALUES ($1, $2, $3, $4, $5)
     RETURNING *`,
    [data.courtId, data.date, data.startTime, data.endTime, data.working]
  );
  client.release();
  return res.rows[0];
}