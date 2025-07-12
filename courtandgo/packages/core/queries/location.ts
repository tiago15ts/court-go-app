import { db } from "../db";

export async function createLocation(data: {
  address: string;
  county: string;
  district: string;
  country: string;
  postalCode: string;
  latitude?: number;
  longitude?: number;
}) {
  const client = await db.connect();
  // 1. Verifica ou cria o país
  const countryRes = await client.query(
    `SELECT countryId FROM Country WHERE name = $1`,
    [data.country]
  );
  let countryId: number;
  if (countryRes.rowCount > 0) {
    countryId = countryRes.rows[0].countryid;
  } else {
    const insertCountry = await client.query(
      `INSERT INTO Country (name) VALUES ($1) RETURNING countryId`,
      [data.country]
    );
    countryId = insertCountry.rows[0].countryid;
  }

  // 2. Verifica ou cria o distrito
  const districtRes = await client.query(
    `SELECT districtId FROM District WHERE name = $1 AND countryId = $2`,
    [data.district, countryId]
  );
  let districtId: number;
  if (districtRes.rowCount > 0) {
    districtId = districtRes.rows[0].districtid;
  } else {
    const insertDistrict = await client.query(
      `INSERT INTO District (name, countryId) VALUES ($1, $2) RETURNING districtId`,
      [data.district, countryId]
    );
    districtId = insertDistrict.rows[0].districtid;
  }

  // 3. Cria a localização
  const locationRes = await client.query(
    `INSERT INTO Location (address, county, districtId, postalCode, latitude, longitude)
     VALUES ($1, $2, $3, $4, $5, $6)
     RETURNING *`,
    [
      data.address,
      data.county,
      districtId,
      data.postalCode,
      data.latitude || null,
      data.longitude || null,
    ]
  );
  client.release();

  return locationRes.rows[0];
}

export async function updateLocation(location: {
  locationId: number;
  address: string;
  county: string;
  districtId: number;
  postalCode: string;
  latitude?: number;
  longitude?: number;
}) {
  const client = await db.connect();
  const res = await client.query(
    `UPDATE Location
     SET address = $1, county = $2, districtId = $3, postalCode = $4,
         latitude = $5, longitude = $6
     WHERE locationId = $7
     RETURNING *`,
    [
      location.address,
      location.county,
      location.districtId,
      location.postalCode,
      location.latitude || null,
      location.longitude || null,
      location.locationId,
    ]
  );
  client.release();
  return res.rows[0];
}

export async function getLocationByClubId(clubId: number) {
  const client = await db.connect();
  const res = await client.query(
    `SELECT l.* FROM Location l
     JOIN Club c ON l.locationId = c.locationId
     WHERE c.clubId = $1`,
    [clubId]
  );
  client.release();
  return res.rows;
}
