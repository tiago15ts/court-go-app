import { db } from "../db";
import { mapRowToReservationDTO } from "../mappers/reservationMapper";
import { scheduleReservationReminder } from "../../../utils/reminders";


export async function getAllReservations() {
  const query = `
    SELECT * FROM Reservation
  `;
  const res = await db.query(query);
  return res.rows.map(mapRowToReservationDTO);
}

export async function getReservationById(id: number) {
  const query = `
    SELECT * FROM Reservation
    WHERE reservationId = $1
  `;
  const res = await db.query(query, [id]);
  if (res.rows.length === 0) return null;
  return mapRowToReservationDTO(res.rows[0]);
}

export async function getReservationsForPlayer(playerId: number) {
  const query = `
    SELECT * FROM Reservation r
    WHERE r.createdByPlayerId = $1
  `;
  const res = await db.query(query, [playerId]);
  return res.rows.map(mapRowToReservationDTO);
}

export async function createReservation(data: {
  courtId: number;
  userId: number;
  startTime: string;      // ISO string
  endTime: string;        // ISO string
  estimatedPrice: number;
  status: string;
}) {
  const res1 = await db.query(
    `INSERT INTO Reservation (courtId, createdByPlayerId, startTime, endTime, estimatedPrice, status)
     VALUES ($1, $2, $3, $4, $5, $6)
     RETURNING *`,
    [data.courtId, data.userId, data.startTime, data.endTime, data.estimatedPrice, data.status]
  );
  const reservation = mapRowToReservationDTO(res1.rows[0]);

  const playerRes = await db.query('SELECT email, name FROM Player WHERE id = $1', [data.userId]);
  const player = playerRes.rows[0];

  const courtRes = await db.query('SELECT clubName FROM Court WHERE id = $1', [data.courtId]);
  const clubName = courtRes.rows[0]?.clubName ?? 'o clube';

  scheduleReservationReminder(reservation, player.email, player.name, clubName);

  await db.query(
    `INSERT INTO Player_Reservation (reservationId, playerId, status)
     VALUES ($1, $2, 'Pending')`,
    [reservation.reservationId, data.userId]
  );
  return reservation;
}

export async function updateReservation(data: {
  reservationId: number;
  startTime: string;      // ISO string
  endTime: string;        // ISO string
  estimatedPrice: number;
  status: string;
}) {
  const res = await db.query(
    `UPDATE Reservation
     SET startTime = $1, endTime = $2, estimatedPrice = $3, status = $4
     WHERE reservationId = $5
     RETURNING *`,
    [data.startTime, data.endTime, data.estimatedPrice, data.status, data.reservationId]
  );

  if (res.rows.length === 0) {
    throw new Error(`Reservation with id ${data.reservationId} not found`);
  }
  return mapRowToReservationDTO(res.rows[0]);
}

export async function deleteReservation(id: number) {
  await db.query(
    `DELETE FROM Player_Reservation WHERE reservationId = $1`,
    [id]
  );
  const res = await db.query(
    `DELETE FROM Reservation WHERE reservationId = $1 RETURNING *`,
    [id]
  );

  if (res.rowCount === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  return true;
}

export async function confirmReservation(id: number) {
  const res = await db.query(
    `UPDATE Reservation SET status = 'Confirmed' WHERE reservationId = $1 RETURNING *`,
    [id]
  );
  if (res.rowCount === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  await db.query(
    `UPDATE Player_Reservation SET status = 'Confirmed' WHERE reservationId = $1`,
    [id]
  );
  return mapRowToReservationDTO(res.rows[0]);
}

export async function cancelReservation(id: number) {
  const res = await db.query(
    `UPDATE Reservation SET status = 'Cancelled' WHERE reservationId = $1 RETURNING *`,
    [id]
  );
  if (res.rowCount === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  await db.query(
    `UPDATE Player_Reservation SET status = 'Cancelled' WHERE reservationId = $1`,
    [id]
  );
  return mapRowToReservationDTO(res.rows[0]);
}


export async function getReservationsByCourtId(courtId: number) {
  const res = await db.query(
    `SELECT * FROM Reservation WHERE courtId = $1`,
    [courtId]
  );

  return res.rows.map(mapRowToReservationDTO);
}

export async function getReservationsByDateRange(startDate: string, endDate: string) {
  const res = await db.query(
    `SELECT * FROM Reservation
     WHERE startTime >= $1 AND endTime <= $2`,
    [startDate, endDate]
  );

  return res.rows.map(mapRowToReservationDTO);
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
  return res.rows.map(mapRowToReservationDTO);
}



