import { db } from "../db";
import { mapRowToCourtDTO } from '../mappers/courtMapper';

export async function getCourtsByClub(clubId: number) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT c.* FROM Court c
     JOIN Club cl ON c.clubid = cl.clubid
     WHERE cl.clubid = $1`,
    [clubId]
  );
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}

export async function getCourtById(id: number) {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Court WHERE courtId = $1", [id]);
  client.release();
  return res.rows.length > 0 ? mapRowToCourtDTO(res.rows[0]) : null;
}

export async function getCourtsByType(type: string) {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Court WHERE type = $1", [type]);
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}


export async function getAllCourts() {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Court");
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}

export async function getCourtsByDistrict(district: string) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT c.* FROM Court c
     JOIN Club cl ON c.clubId = cl.clubId
     JOIN Location l ON cl.locationId = l.locationId
     JOIN District di ON l.districtId = di.districtId
     WHERE di.name ILIKE $1`,
    [district]
  );
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}

export async function getCourtsBySport(sport: string) {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Court WHERE type = $1", [sport]);
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}

//not in use
export async function getCourtsFiltered(district: string, sport: string) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT c.* FROM Court c
     JOIN Club cl ON c.clubId = cl.clubId
     JOIN Location l ON cl.locationId = l.locationId
     JOIN District di ON l.districtId = di.districtId
     WHERE di.name ILIKE $1 AND c.type = $2`,
    [district, sport]
  );
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}

//not in use
export async function getCourtsByOwnerId(ownerId: number) {
  const client = await db.connect();
  //const res = await client.query("SELECT * FROM Court WHERE ownerId = $1", [ownerId]);
  client.release();
  return [];
}

export async function getCourtsByLocationId(locationId: number) {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Court WHERE locationId = $1", [locationId]);
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}

export async function getCourtsByName(name: string) {
  const client = await db.connect();
  const res = await client.query("SELECT * FROM Court WHERE name ILIKE $1", [`%${name}%`]);
  client.release();
  return res.rows.map(mapRowToCourtDTO);
}

//funcoes exclusivas para admin
export async function createCourt(court: {
  name: string;
  clubId: number;
  type: string;
  surfaceType: string;
  capacity: number;
  pricePerHour: number;
}) {
  const client = await db.connect();
  const res = await client.query(
    `INSERT INTO Court (name, clubId, type, surfaceType, capacity, pricePerHour)
     VALUES ($1, $2, $3, $4, $5, $6)
     RETURNING *`,
    [court.name, court.clubId, court.type, court.surfaceType, court.capacity, court.pricePerHour]
  );
  client.release();
  return res.rows[0];
}

export async function updateCourt(court: {
  courtId: number;
  name?: string;
  clubId?: number;
  type?: string;
  surfaceType?: string;
  capacity?: number;
  pricePerHour?: number;
}) {
  const client = await db.connect();
  const fields: string[] = [];
  const values: any[] = [];
  let i = 1;

  if (court.name !== undefined) {
    fields.push(`name = $${i++}`);
    values.push(court.name);
  }
  if (court.clubId !== undefined) {
    fields.push(`clubId = $${i++}`);
    values.push(court.clubId);
  }
  if (court.type !== undefined) {
    fields.push(`type = $${i++}`);
    values.push(court.type);
  }
  if (court.surfaceType !== undefined) {
    fields.push(`surfaceType = $${i++}`);
    values.push(court.surfaceType);
  }
  if (court.capacity !== undefined) {
    fields.push(`capacity = $${i++}`);
    values.push(court.capacity);
  }
  if (court.pricePerHour !== undefined) {
    fields.push(`pricePerHour = $${i++}`);
    values.push(court.pricePerHour);
  }

  if (fields.length === 0) {
    throw new Error("Nenhum campo para atualizar");
  }

  values.push(court.courtId);

  const res = await client.query(
    `UPDATE Court SET ${fields.join(", ")} WHERE courtId = $${i} RETURNING *`,
    values
  );
  client.release();
  return res.rows[0];
}


