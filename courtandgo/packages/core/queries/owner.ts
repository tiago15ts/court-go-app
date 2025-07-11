import { db } from "../db";

export async function createOwner(owner: {
  email: string;
  name: string;
  phone: string;
}) {
  const client = await db.connect();
  const res = await client.query(
    `INSERT INTO Owner (name, email, phone)
     VALUES ($1, $2, $3)
     RETURNING *`,
    [owner.name, owner.email, owner.phone]
  );
  client.release();
  return res.rows[0];
}



