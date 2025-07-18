import { db } from "../db";
import { mapRowToReservationDTO } from "../mappers/reservationMapper";
import { scheduleReservationReminder } from "../../../utils/reminders";


export async function getAllReservations() {
  const client = await db.connect();
  const query = `
    SELECT * FROM Reservation
  `;
  const res = await client.query(query);
  client.release();
  return res.rows.map(mapRowToReservationDTO);
}

export async function getReservationById(id: number) {
  const client = await db.connect();
  const query = `
    SELECT * FROM Reservation
    WHERE reservationId = $1
  `;
  const res = await client.query(query, [id]);
  client.release();
  if (res.rows.length === 0) return null;
  return mapRowToReservationDTO(res.rows[0]);
}

export async function getReservationsForPlayer(playerId: number) {
  const client = await db.connect();
  const query = `
    SELECT * FROM Reservation r
    WHERE r.createdByPlayerId = $1
  `;
  const res = await client.query(query, [playerId]);
  client.release();
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
  const client = await db.connect();
  const res1 = await client.query(
    `INSERT INTO Reservation (courtId, createdByPlayerId, startTime, endTime, estimatedPrice, status)
     VALUES ($1, $2, $3, $4, $5, $6)
     RETURNING *`,
    [data.courtId, data.userId, data.startTime, data.endTime, data.estimatedPrice, data.status]
  );
  const reservation = mapRowToReservationDTO(res1.rows[0]);

  const playerRes = await client.query('SELECT email, name FROM Player WHERE playerId = $1', [data.userId]);
  const player = playerRes.rows[0];

  const courtRes = await client.query(
    `SELECT c.name AS clubName
     FROM Court ct
     JOIN Club c ON ct.clubId = c.clubId
     WHERE ct.courtId = $1`,
    [data.courtId]
  );
  const clubName = courtRes.rows[0]?.clubname ?? 'o clube';

  const prefRes = await client.query(
    'SELECT emailnotifications FROM Player WHERE playerId = $1',
    [data.userId]
  );
  const emailEnabled = prefRes.rows[0]?.emailnotifications;

  if (emailEnabled) {
    scheduleReservationReminder(reservation, player.email, player.name, clubName);
  }

  await client.query(
    `INSERT INTO Player_Reservation (reservationId, playerId, status)
     VALUES ($1, $2, 'Pending')`,
    [reservation.reservationId, data.userId]
  );
  client.release();
  return reservation;
}

export async function updateReservation(data: {
  reservationId: number;
  startTime: string;      // ISO string
  endTime: string;        // ISO string
  estimatedPrice: number;
  status: string;
}) {
  const client = await db.connect();
  const res = await client.query(
    `UPDATE Reservation
     SET startTime = $1, endTime = $2, estimatedPrice = $3, status = $4
     WHERE reservationId = $5
     RETURNING *`,
    [data.startTime, data.endTime, data.estimatedPrice, data.status, data.reservationId]
  );
  client.release();

  if (res.rows.length === 0) {
    throw new Error(`Reservation with id ${data.reservationId} not found`);
  }
  return mapRowToReservationDTO(res.rows[0]);
}

export async function deleteReservation(id: number) {
  const client = await db.connect();
  await client.query(
    `DELETE FROM Player_Reservation WHERE reservationId = $1`,
    [id]
  );
  const res = await client.query(
    `DELETE FROM Reservation WHERE reservationId = $1 RETURNING *`,
    [id]
  );

  client.release();
  if (res.rowCount === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  return true;
}

export async function confirmReservation(id: number) {
  const client = await db.connect();
  const res = await client.query(
    `UPDATE Reservation SET status = 'Confirmed' WHERE reservationId = $1 RETURNING *`,
    [id]
  );
  if (res.rowCount === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  await client.query(
    `UPDATE Player_Reservation SET status = 'Confirmed' WHERE reservationId = $1`,
    [id]
  );
  client.release();
  if (res.rows.length === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  return true;
}

export async function cancelReservation(id: number) {
  const client = await db.connect();
  const res = await client.query(
    `UPDATE Reservation SET status = 'Cancelled' WHERE reservationId = $1 RETURNING *`,
    [id]
  );
  if (res.rowCount === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  await client.query(
    `UPDATE Player_Reservation SET status = 'Cancelled' WHERE reservationId = $1`,
    [id]
  );
  client.release();
  if (res.rows.length === 0) {
    throw new Error(`Reservation with id ${id} not found`);
  }
  return true;
}

//no api route for this function
export async function getReservationsByCourtId(courtId: number) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT * FROM Reservation WHERE courtId = $1`,
    [courtId]
  );
  client.release();
  return res.rows.map(mapRowToReservationDTO);
}

//no api route for this function
export async function getReservationsByDateRange(startDate: string, endDate: string) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT * FROM Reservation
     WHERE startTime >= $1 AND endTime <= $2`,
    [startDate, endDate]
  );
  client.release();
  return res.rows.map(mapRowToReservationDTO);
}


export async function getReservationsByCourtIdsAndDate(
  courtIds: number[],
  date: string // 'YYYY-MM-DD'
) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT * FROM Reservation
     WHERE courtId = ANY($1)
     AND DATE(startTime) = $2`,
    [courtIds, date]
  );
  client.release();
  return res.rows.map(mapRowToReservationDTO);
}



