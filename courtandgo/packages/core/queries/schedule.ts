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

//not in use
export async function getWeeklyScheduleById(scheduleId: number) {
  const client = await db.connect();
  const res = await client.query(
    "SELECT * FROM WeeklySchedule WHERE scheduleId = $1",
    [scheduleId]
  );
  client.release();
  return res.rows.length > 0 ? mapRowToWeeklyScheduleDTO(res.rows[0]) : null;
}

//not in use
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
  clubId: number;
  dayOfWeek: string; // 'Monday', 'Tuesday', etc.
  startTime: string;
  endTime: string;
}) {
  const client = await db.connect();

  // 1. Apagar horários antigos desse clube para o mesmo dia da semana
  await client.query(
    `
      DELETE FROM WeeklySchedule
      WHERE courtId IN (
        SELECT courtId FROM Court WHERE clubId = $1
      )
      AND dayOfWeek = $2;
      `,
    [data.clubId, data.dayOfWeek]
  );

  // 2. Inserir novos horários para os courts do clube
  const res = await client.query(
    `
      INSERT INTO WeeklySchedule (courtId, dayOfWeek, startTime, endTime)
      SELECT courtId, $2, $3, $4
      FROM Court
      WHERE clubId = $1
      RETURNING *;
      `,
    [data.clubId, data.dayOfWeek, data.startTime, data.endTime]
  );

  client.release();
  return res.rows;
}

export async function createSpecialSchedule(data: {
  clubId: number;
  date: string; // yyyy-mm-dd
  startTime: string;
  endTime: string;
  working: boolean;
}) {
  const client = await db.connect();

  const res = await client.query(
    `
    INSERT INTO SpecialSchedule (courtId, date, startTime, endTime, working)
    SELECT courtId, $2, $3, $4, $5
    FROM Court
    WHERE clubId = $1
    ON CONFLICT (courtId, date)
    DO UPDATE SET
      startTime = EXCLUDED.startTime,
      endTime = EXCLUDED.endTime,
      working = EXCLUDED.working
    RETURNING *;
    `,
    [data.clubId, data.date, data.startTime, data.endTime, data.working]
  );

  client.release();

  // Normalizar a data no output
  for (const row of res.rows) {
    if (row.date instanceof Date) {
      row.date = row.date.toISOString().slice(0, 10);
    }
  }

  return res.rows;
}
