import { db } from "../db";

export async function getAllReservations() {
  const res = await db.query("SELECT * FROM Reservation");
  return res.rows;
}

export async function getReservationById(id: number) {
  const res = await db.query("SELECT * FROM Reservation WHERE reservationId = $1", [id]);
  return res.rows[0] || null;
}

export async function getReservationsForPlayer(playerId: number) {
  const res = await db.query(
    `SELECT r.* FROM Reservation r
     JOIN Player_Reservation pr ON r.reservationId = pr.reservationId
     WHERE pr.playerId = $1`,
    [playerId]
  );
  return res.rows;
}

export async function createReservation(data: any) {
  const res = await db.query(
    `INSERT INTO Reservation (courtId, createdByPlayerId, startTime, endTime, estimatePrice, status)
     VALUES ($1, $2, $3, $4, $5, $6)
     RETURNING *`,
    [data.courtId, data.createdByPlayerId, data.startTime, data.endTime, data.estimatePrice, data.status]
  );
  return res.rows[0];
}

export async function updateReservation(data: any) {
  const res = await db.query(
    `UPDATE Reservation
     SET startTime = $1, endTime = $2, estimatePrice = $3, status = $4
     WHERE reservationId = $5
     RETURNING *`,
    [data.startTime, data.endTime, data.estimatePrice, data.status, data.reservationId]
  );
  return res.rows[0];
}

export async function deleteReservation(id: number) {
  await db.query("DELETE FROM Reservation WHERE reservationId = $1", [id]);
  return true;
}

export async function confirmReservation(id: number) {
  await db.query(
    `UPDATE Reservation SET status = 'Confirmed' WHERE reservationId = $1`,
    [id]
  );
  return true;
}

export async function cancelReservation(id: number) {
  await db.query(
    `UPDATE Reservation SET status = 'Cancelled' WHERE reservationId = $1`,
    [id]
  );
  return true;
}

export async function getReservationsByCourtId(courtId: number) {
  const res = await db.query("SELECT * FROM Reservation WHERE courtId = $1", [courtId]);
  return res.rows;
}

export async function getReservationsByDateRange(startDate: string, endDate: string) {
  const res = await db.query(
    `SELECT * FROM Reservation
     WHERE startTime >= $1 AND endTime <= $2`,
    [startDate, endDate]
  );
  return res.rows;
}

export async function getReservationsByCourtIdsAndDate(
  courtIds: number[],
  date: string // 'YYYY-MM-DD'
) {
  const res = await db.query(
    `SELECT * FROM Reservation
     WHERE courtId = ANY($1)
     AND DATE(startTime) = $2`,
    [courtIds, date]
  );
  return res.rows;
}

