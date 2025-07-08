import { db } from "../db";
import { mapRowToClubDTO } from "../mappers/clubMapper";

const BASE_CLUB_SELECT = `
  SELECT 
    c.clubId, c.name AS club_name, c.sports, c.nrOfCourts,
    l.locationId AS location_id, l.address, l.county, l.postalCode,
    l.latitude, l.longitude,
    d.districtId AS district_id, d.name AS district_name, d.countryId AS district_country_id,
    co.countryId AS country_id, co.name AS country_name
  FROM Club c
  JOIN Location l ON c.locationId = l.locationId
  JOIN District d ON l.districtId = d.districtId
  JOIN Country co ON d.countryId = co.countryId
`;

export async function getAllClubs() {
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
      c.name AS country_name

    FROM Club cl
    JOIN Location l ON cl.locationId = l.locationId
    JOIN District d ON l.districtId = d.districtId
    JOIN Country c ON d.countryId = c.countryId
  `;

  const res = await db.query(query);
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByDistrict(district: string) {
  const res = await db.query(
    `${BASE_CLUB_SELECT}
    WHERE d.name ILIKE $1
    `,
    [district]
  );

  return res.rows.map(mapRowToClubDTO);
}


export async function getClubsByCounty(county: string) {
  const res = await db.query(
    `${BASE_CLUB_SELECT} WHERE l.county ILIKE $1`,
    [county]
  );
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByCountry(country: string) {
  const res = await db.query(
    `${BASE_CLUB_SELECT} WHERE co.name ILIKE $1`,
    [country]
  );
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByPostalCode(postalCode: string) {
  const res = await db.query(
    `${BASE_CLUB_SELECT} WHERE l.postalCode ILIKE $1`,
    [postalCode]
  );
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsByName(name: string) {
  const res = await db.query(
    `${BASE_CLUB_SELECT} WHERE c.name ILIKE $1`,
    [`%${name}%`]
  );
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubsBySport(sport: string) {
  const res = await db.query(
    `${BASE_CLUB_SELECT} WHERE c.sports = $1`,
    [sport]
  );
  return res.rows.map(mapRowToClubDTO);
}

export async function getClubById(id: number) {
  const res = await db.query(
    `${BASE_CLUB_SELECT} WHERE c.clubId = $1`,
    [id]
  );
  return res.rows.length > 0 ? mapRowToClubDTO(res.rows[0]) : null;
}

export async function getClubsByOwnerId(ownerId: number) {
  const res = await db.query(
    `${BASE_CLUB_SELECT} WHERE c.ownerId = $1`,
    [ownerId]
  );
  return res.rows.map(mapRowToClubDTO);
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
  `;

  const res = await db.query(query, values);
  return res.rows.map(mapRowToClubDTO);
}

//queries exclusivas para admins.
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


