import { db } from "../db";

export async function getUserById(id: number) {
  const res = await db.query("SELECT * FROM Player WHERE playerId = $1", [id]);
  return res.rows[0] || null;
}

export async function getUserByEmail(email: string) {
  const res = await db.query("SELECT * FROM Player WHERE email = $1", [email]);
  return res.rows[0] || null;
}

export async function registerUser(user: {
  email: string;
  name: string;
  countryId: number;
  phone: string;
}) {
  const res = await db.query(
    `INSERT INTO Player (email, name, countryId, phone)
     VALUES ($1, $2, $3, $4)
     RETURNING *`,
    [user.email, user.name, user.countryId, user.phone]
  );
  return res.rows[0];
}

export async function updateUser(user: any) {
  const res = await db.query(
    `UPDATE Player SET name = $1, phone = $2, countryId = $3, birthdate = $5, weight = $6, height = $7, gender = $8
     WHERE playerId = $4 RETURNING *`,
    [user.name, user.phone, user.countryId, user.playerId, user.birthdate, user.weight, user.height, user.gender]
  );
  return res.rows[0];
}

export async function deleteUser(id: number) {
  const res = await db.query("DELETE FROM Player WHERE playerId = $1 RETURNING *", [id]);
  return res.rows[0];
}

export async function getAllUsers() {
  const res = await db.query("SELECT * FROM Player");
  return res.rows;
}

export async function getUserReservations(userId: number) {
  const res = await db.query(
    `SELECT r.reservationId, r.courtId, r.startTime, r.endTime, c.name AS courtName
     FROM Reservation r
     JOIN Court c ON r.courtId = c.courtId
     WHERE r.playerId = $1`,
    [userId]
  );
  return res.rows;
}

