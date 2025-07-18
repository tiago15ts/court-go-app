import { db } from "../db";
import { mapRowToUserDTO } from "../mappers/userMapper";

export async function getUserById(id: number) {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Player WHERE playerid = $1", [id]);
  client.release();
  return res.rows[0] ? mapRowToUserDTO(res.rows[0]) : null;
}

export async function getUserByEmail(email: string) {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Player WHERE email = $1", [email]);
  client.release();
  return res.rows[0] ? mapRowToUserDTO(res.rows[0]) : null;
}

export async function registerUser(user: {
  email: string;
  name: string;
  countryId: string;
  phone: string;
}) {
  const client = await db.connect();
  const res = await client.query(
    `INSERT INTO Player (email, name, countryId, phone)
     VALUES ($1, $2, $3, $4)
     RETURNING *`,
    [user.email, user.name, user.countryId, user.phone]
  );
  client.release();
  return mapRowToUserDTO(res.rows[0]);
}

export async function updateUser(user: any) {
  const client = await db.connect();
  const res = await client.query(
    `UPDATE Player
     SET name = $1, phone = $2, countryId = $3, birthdate = $4, weight = $5, height = $6, gender = $7
     WHERE playerId = $8
     RETURNING *`,
    [
      user.name,
      user.phone,
      user.countryCode,
      user.birthdate,
      user.weight,
      user.height,
      user.gender,
      user.id
    ]
  );
  if (res.rows.length === 0) {
    throw new Error(`No user found with playerId ${user.id}`);
  }
  client.release();
  return mapRowToUserDTO(res.rows[0]);
}

export async function deleteUser(id: number) {
  const client = await db.connect();
  const res = await client.query("DELETE FROM Player WHERE playerId = $1 RETURNING *", [id]);
  client.release();
  return mapRowToUserDTO(res.rows[0]);
}

export async function getAllUsers() {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Player");
  client.release();
  return res.rows.map(mapRowToUserDTO);
}

export async function emailNotifications(id: number, enabled: boolean) {
  const client = await db.connect();
  const res = await client.query(
    `UPDATE Player SET emailNotifications = $1 WHERE playerId = $2`,
    [enabled, id]
  );
  client.release();
  return res.rowCount > 0;
}
