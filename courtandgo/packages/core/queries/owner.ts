import { db } from "../db";

export async function createOwner(owner: {
  email: string;
  name: string;
  phone: string;
}) {
  const res = await db.query(
    `INSERT INTO Player (email, name, phone)
     VALUES ($1, $2, $3)
     RETURNING *`,
    [owner.email, owner.name, owner.phone]
  );
  return res.rows[0];
}



