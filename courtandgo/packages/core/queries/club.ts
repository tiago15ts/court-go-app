import { db } from "../db";
import { mapRowToClubDTO } from "../mappers/clubMapper";



const BASE_CLUB_SELECT = `
  SELECT 
    c.clubId, c.name AS club_name, c.sports, c.nrOfCourts,
    l.locationId AS location_id, l.address, l.county, l.postalCode,
    l.latitude, l.longitude,
    d.districtId AS district_id, d.name AS district_name, d.countryId AS district_country_id,
    co.countryId AS country_id, co.name AS country_name,
    AVG(cor.pricePerHour) AS averageprice
  FROM Club c
  JOIN Location l ON c.locationId = l.locationId
  JOIN District d ON l.districtId = d.districtId
  JOIN Country co ON d.countryId = co.countryId
  LEFT JOIN Court cor ON c.clubId = cor.clubId
`;

const BASE_CLUB_GROUP_BY = `
  GROUP BY 
    c.clubId, c.name, c.sports, c.nrOfCourts,
    l.locationId, l.address, l.county, l.postalCode, l.latitude, l.longitude,
    d.districtId, d.name, d.countryId,
    co.countryId, co.name
`;

export async function getAllClubs() {
  const client = await db.connect();
  const query = `
    SELECT
      cl.clubId,
      cl.name AS club_name,
      cl.sports,
      cl.nrOfCourts,

      l.locationId AS location_id,
      l.address,
      l.county,
      l.postalCode,
      l.latitude,
      l.longitude,

      d.districtId AS district_id,
      d.name AS district_name,
      d.countryId AS district_country_id,

      c.countryId AS country_id,
      c.name AS country_name,
      AVG(cor.pricePerHour) AS averageprice

    FROM Club cl
    JOIN Location l ON cl.locationId = l.locationId
    JOIN District d ON l.districtId = d.districtId
    JOIN Country c ON d.countryId = c.countryId
       LEFT JOIN Court cor ON cl.clubId = cor.clubId

    GROUP BY
      cl.clubId, cl.name, cl.sports, cl.nrOfCourts,
      l.locationId, l.address, l.county, l.postalCode, l.latitude, l.longitude,
      d.districtId, d.name, d.countryId,
      c.countryId, c.name
  `;
  const res = await client.query(query);
  client.release();
  
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByDistrict(district: string) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT}
    WHERE d.name ILIKE $1
    ${BASE_CLUB_GROUP_BY}
    `,
    [district]
  );
  client.release();

  return res.rows.map(mapRowToClubDTO);
}


export async function getClubsByCounty(county: string) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT} WHERE l.county ILIKE $1
    ${BASE_CLUB_GROUP_BY}`,
    [county]
  );
  client.release();
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByCountry(country: string) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT} WHERE co.name ILIKE $1
    ${BASE_CLUB_GROUP_BY}`,
    [country]
  );
  client.release();
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByPostalCode(postalCode: string) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT} WHERE l.postalCode ILIKE $1
    ${BASE_CLUB_GROUP_BY}`,
    [postalCode]
  );
  client.release();
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByName(name: string) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT} WHERE c.name ILIKE $1
    ${BASE_CLUB_GROUP_BY}`,
    [`%${name}%`]
  );
  client.release();
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsBySport(sport: string) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT} WHERE c.sports = $1
    ${BASE_CLUB_GROUP_BY}`,
    [sport]
  );
  client.release();
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubById(id: number) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT} WHERE c.clubId = $1
    ${BASE_CLUB_GROUP_BY}`,
    [id]
  );
  client.release();
  return res.rows.length > 0 ? mapRowToClubDTO(res.rows[0]) : null;
}

export async function getClubsByOwnerId(ownerId: number) {
  const client = await db.connect();
  const res = await client.query(
    `${BASE_CLUB_SELECT} WHERE c.ownerId = $1
    ${BASE_CLUB_GROUP_BY}`,
    [ownerId]
  );
  client.release();
  return res.rows.map(mapRowToClubDTO);
}

// get clubId by courtId
export async function getClubIdByCourtId(courtId: number) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT cl.clubId FROM Club cl
     JOIN Court c ON cl.clubId = c.clubId
     WHERE c.courtId = $1`,
    [courtId]
  );
  client.release();
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
  const client = await db.connect();
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
    conditions.push(`di.name ILIKE $${i++}`);
    values.push(params.district);
  }

  if (params.country) {
    conditions.push(`co.name ILIKE $${i++}`);
    values.push(params.country);
  }

  if (params.postalCode) {
    conditions.push(`l.postalCode ILIKE $${i++}`);
    values.push(params.postalCode);
  }

  if (params.sport) {
    conditions.push(`cl.sports = $${i++}`);
    values.push(params.sport);
  }

  const query = `
    ${BASE_CLUB_SELECT}
    WHERE ${conditions.join(" AND ")}
    ${BASE_CLUB_GROUP_BY}
  `;

  const res = await client.query(query, values);
  client.release();
  return res.rows.map(mapRowToClubDTO);
}


export async function getClubNameByCourtId(courtId: number) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT c.name AS club_name
     FROM Club c
     JOIN Court cor ON c.clubId = cor.clubId
     WHERE cor.courtId = $1`,
    [courtId]
  );

  client.release();
  return res.rows.length > 0 ? res.rows[0].club_name : null;
}

//queries exclusivas para admins.
export async function createClub(club: {
  name: string;
  sports: string;
  nrOfCourts: number;
  locationId: number;
  ownerId: number;
}) {
  const client = await db.connect();
  const res = await client.query(
    `INSERT INTO Club (name, sports, nrOfCourts, locationId, ownerId)
     VALUES ($1, $2, $3, $4, $5)
     RETURNING *`,
    [club.name, club.sports, club.nrOfCourts, club.locationId, club.ownerId]
  );
  client.release();
  return res.rows[0];
}

export async function updateClub(club: {
  clubId: number;
  name?: string;
  sports?: string;
  nrOfCourts?: number;
  locationId?: number;
}) {
  const client = await db.connect();
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

  const res = await client.query(query, values);
  client.release();
  return res.rows[0];
}


