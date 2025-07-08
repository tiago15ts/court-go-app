import { db } from "../db";

export async function getCourtsByClub(clubId: number) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Club cl ON c.clubId = cl.clubId
     WHERE cl.clubId = $1`,
    [clubId]
  );
  return res.rows;
}

export async function getCourtById(id: number) {
  const res = await db.query("SELECT * FROM Court WHERE courtId = $1", [id]);
  return res.rows[0] || null;
}

export async function getCourtsByType(type: string) {
  const res = await db.query("SELECT * FROM Court WHERE type = $1", [type]);
  return res.rows;
}


export async function getAllCourts() {
  const res = await db.query("SELECT * FROM Court");
  return res.rows;
}

export async function getCourtsByDistrict(district: string) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Club cl ON c.clubId = cl.clubId
     JOIN Location l ON cl.locationId = l.locationId
     JOIN District di ON l.districtId = di.districtId
     WHERE di.name ILIKE $1`,
    [district]
  );
  return res.rows;
}

export async function getCourtsBySport(sport: string) {
  const res = await db.query("SELECT * FROM Court WHERE type = $1", [sport]);
  return res.rows;
}

export async function getCourtsFiltered(district: string, sport: string) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Club cl ON c.clubId = cl.clubId
     JOIN Location l ON cl.locationId = l.locationId
     JOIN District di ON l.districtId = di.districtId
     WHERE di.name ILIKE $1 AND c.type = $2`,
    [district, sport]
  );
  return res.rows;
}

export async function getCourtsByOwnerId(ownerId: number) {
  const res = await db.query("SELECT * FROM Court WHERE ownerId = $1", [ownerId]);
  return res.rows;
}

export async function getCourtsByLocationId(locationId: number) {
  const res = await db.query("SELECT * FROM Court WHERE locationId = $1", [locationId]);
  return res.rows;
}

export async function getCourtsByName(name: string) {
  const res = await db.query("SELECT * FROM Court WHERE name ILIKE $1", [`%${name}%`]);
  return res.rows;
}

export async function getCourtsByCity(city: string) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Location l ON c.locationId = l.locationId
     JOIN City ci ON l.cityId = ci.cityId
     WHERE ci.name ILIKE $1`,
    [city]
  );
  return res.rows;
}

export async function getCourtsByCountry(country: string) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Location l ON c.locationId = l.locationId
     JOIN City ci ON l.cityId = ci.cityId
     JOIN Country co ON ci.countryId = co.countryId
     WHERE co.name ILIKE $1`,
    [country]
  );
  return res.rows;
}

export async function getCourtsByRegion(region: string) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Location l ON c.locationId = l.locationId
     JOIN City ci ON l.cityId = ci.cityId
     JOIN Region r ON ci.regionId = r.regionId
     WHERE r.name ILIKE $1`,
    [region]
  );
  return res.rows;
}

export async function getCourtsByPostalCode(postalCode: string) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Location l ON c.locationId = l.locationId
     WHERE l.postalCode ILIKE $1`,
    [postalCode]
  );
  return res.rows;
}

export async function getCourtsByAddress(address: string) {
  const res = await db.query(
    `SELECT c.* FROM Court c
     JOIN Location l ON c.locationId = l.locationId
     WHERE l.address ILIKE $1`,
    [`%${address}%`]
  );
  return res.rows;
}

export async function createCourt(court: {
  name: string;
  clubId: number;
  type: string;
  surfaceType: string;
  capacity: number;
  pricePerHour: number;
}) {
  const res = await db.query(
    `INSERT INTO Court (name, clubId, type, surfaceType, capacity, pricePerHour)
     VALUES ($1, $2, $3, $4, $5, $6)
     RETURNING *`,
    [court.name, court.clubId, court.type, court.surfaceType, court.capacity]
  );
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

  const res = await db.query(
    `UPDATE Court SET ${fields.join(", ")} WHERE courtId = $${i} RETURNING *`,
    values
  );
  return res.rows[0];
}


