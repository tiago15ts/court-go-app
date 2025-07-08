import { db } from "../db";

export async function getAllClubs() {
  const res = await db.query("SELECT * FROM Club");
  return res.rows;
}

export async function getClubsByDistrict(district: string) {
  const res = await db.query(
    `SELECT c.* FROM Club c
     JOIN Location l ON c.locationId = l.locationId
     JOIN District di ON l.districtId = di.districtId
     WHERE di.name ILIKE $1`,
    [district]
  );
  return res.rows;
}

export async function getClubsByCounty(county: string) {
  const res = await db.query(
    `SELECT c.* FROM Club c
     JOIN Location l ON c.locationId = l.locationId
     WHERE l.name ILIKE $1`,
    [county]
  );
  return res.rows;
}

export async function getClubsByCountry(country: string) {
  const res = await db.query(
    `SELECT c.* FROM Club c
     JOIN Location l ON c.locationId = l.locationId
     JOIN District di ON l.districtId = di.districtId
     JOIN Country co ON di.countryId = co.countryId
     WHERE co.name ILIKE $1`,
    [country]
  );
  return res.rows;
}

export async function getClubsByPostalCode(postalCode: string) {
  const res = await db.query(
    `SELECT * FROM Club WHERE postalCode ILIKE $1`,
    [postalCode]
  );
  return res.rows;
}

export async function getClubsByName(name: string) {
  const res = await db.query(
    `SELECT * FROM Club WHERE name ILIKE $1`,
    [`%${name}%`]
  );
  return res.rows;
}

export async function getClubsBySport(sport: string) {
  const res = await db.query(
    `SELECT * FROM Club WHERE sports = $1`,
    [sport]
  );
  return res.rows;
}

export async function getClubById(id: number) {
  const res = await db.query(
    `SELECT * FROM Club WHERE clubId = $1`,
    [id]
  );
  return res.rows[0] || null;
}

export async function getClubsByOwnerId(ownerId: number) {
  const res = await db.query(
    `SELECT * FROM Club WHERE ownerId = $1`,
    [ownerId]
  );
  return res.rows;
}

export async function getClubIdByCourtId(courtId: number) {
  const res = await db.query(
    `SELECT cl.clubId FROM Club cl
     JOIN Court c ON cl.clubId = c.clubId
     WHERE c.courtId = $1`,
    [courtId]
  );
  return res.rows[0]?.clubId || null;
}

export async function getClubsFiltered(params: {
  query?: string;
  county?: string;
  district?: string;
  country?: string;
  postalCode?: string;
  sport: string;
}) {
  const conditions: string[] = ["1=1"];
  const values: any[] = [];
  let i = 1;

  if (params.query) {
    conditions.push(`cl.name ILIKE $${i++}`);
    values.push(`%${params.query}%`);
  }

  if (params.county) {
    conditions.push(`l.county ILIKE $${i++}`);
    values.push(params.county);
  }

  if (params.district) {
    conditions.push(`ci.name ILIKE $${i++}`);
    values.push(params.district);
  }

  if (params.country) {
    conditions.push(`co.name ILIKE $${i++}`);
    values.push(params.country);
  }

  if (params.postalCode) {
    conditions.push(`cl.postalCode ILIKE $${i++}`);
    values.push(params.postalCode);
  }

  if (params.sport) {
    conditions.push(`cl.sports = $${i++}`);
    values.push(params.sport);
  }

  const query = `
    SELECT cl.* FROM Club cl
    JOIN Location l ON cl.locationId = l.locationId
    JOIN District di ON l.districtId = di.districtId
    JOIN Country co ON di.countryId = co.countryId
    WHERE ${conditions.join(" AND ")}
  `;

  const res = await db.query(query, values);
  return res.rows;
}

export async function createClub(club: {
  name: string;
  sports: string;
  nrOfCourts: number;
  locationId: number;
  ownerId: number;
}) {
  const res = await db.query(
    `INSERT INTO Club (name, sports, nrOfCourts, locationId, ownerId)
     VALUES ($1, $2, $3, $4, $5)
     RETURNING *`,
    [club.name, club.sports, club.nrOfCourts, club.locationId, club.ownerId]
  );
  return res.rows[0];
}

export async function updateClub(club: {
  clubId: number;
  name?: string;
  sports?: string;
  nrOfCourts?: number;
  locationId?: number;
}) {
  const fields: string[] = [];
  const values: any[] = [];
  let i = 1;

  if (club.name !== undefined) {
    fields.push(`name = $${i++}`);
    values.push(club.name);
  }
  if (club.sports !== undefined) {
    fields.push(`sports = $${i++}`);
    values.push(club.sports);
  }
  if (club.nrOfCourts !== undefined) {
    fields.push(`nrOfCourts = $${i++}`);
    values.push(club.nrOfCourts);
  }
  if (club.locationId !== undefined) {
    fields.push(`locationId = $${i++}`);
    values.push(club.locationId);
  }

  if (fields.length === 0) {
    throw new Error("Nenhum campo fornecido para atualizar.");
  }


  values.push(club.clubId);
  const query = `UPDATE Club SET ${fields.join(", ")} WHERE clubId = $${values.length} RETURNING *`;

  const res = await db.query(query, values);
  return res.rows[0];
}


