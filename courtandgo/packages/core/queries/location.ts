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
// Normalizar o nome do distrito (opcional mas recomendado)
const rawDistrict = data.district.trim();
const normalizedDistrict =
  rawDistrict.charAt(0).toUpperCase() + rawDistrict.slice(1).toLowerCase();

// 1. Tenta encontrar o distrito (case-insensitive)
const districtRes = await client.query(
  `SELECT districtId FROM District WHERE name ILIKE $1 AND countryId = $2`,
  [normalizedDistrict, countryId]
);

let districtId: number;
if (districtRes.rowCount > 0) {
  districtId = districtRes.rows[0].districtid;
} else {
  // 2. Inserir o distrito normalizado
  const insertDistrict = await client.query(
    `INSERT INTO District (name, countryId) VALUES ($1, $2) RETURNING districtId`,
    [normalizedDistrict, countryId]
  );
  districtId = insertDistrict.rows[0].districtid;
}

  // 3. Cria a localização
function capitalizeFirst(str: string): string {
  return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

function normalizeTextField(value: string): string {
  return capitalizeFirst(value.trim());
}

// Normalizar campos
const normalizedAddress = data.address.trim();
const normalizedCounty = normalizeTextField(data.county);
const normalizedPostalCode = data.postalCode.trim();

const locationRes = await client.query(
  `INSERT INTO Location (address, county, districtId, postalCode, latitude, longitude)
   VALUES ($1, $2, $3, $4, $5, $6)
   RETURNING *`,
  [
    normalizedAddress,
    normalizedCounty,
    districtId,
    normalizedPostalCode,
    data.latitude ?? null,
    data.longitude ?? null,
  ]
);

  client.release();

  return locationRes.rows[0];
}

export async function updateLocation(location: {
  locationId: number;
  address: string;
  county: string;
  district: string;
  postalCode: string;
  latitude?: number;
  longitude?: number;
}) {
  const client = await db.connect();
  const defaultCountryId = 1000; // portugal

function normalizeDistrictName(name: string): string {
  const trimmed = name.trim();
  return trimmed.charAt(0).toUpperCase() + trimmed.slice(1).toLowerCase();
}

const normalizedDistrict = normalizeDistrictName(location.district);

const districtRes = await client.query(
  `SELECT districtId FROM District WHERE LOWER(name) = LOWER($1) LIMIT 1`,
  [normalizedDistrict]
);

let districtId: number;

if (districtRes.rows.length > 0) {
  districtId = districtRes.rows[0].districtid;
} else {
  // Criar distrito normalizado com countryId por defeito
  const insertDistrictRes = await client.query(
    `INSERT INTO District (name, countryId)
     VALUES ($1, $2)
     RETURNING districtId`,
    [normalizedDistrict, defaultCountryId]
  );

  districtId = insertDistrictRes.rows[0].districtid;
}
function capitalizeFirst(str: string): string {
  return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

function normalizeTextField(value: string): string {
  return capitalizeFirst(value.trim());
}

// Normalização dos campos antes do UPDATE
const normalizedAddress = location.address.trim();
const normalizedCounty = normalizeTextField(location.county);
const normalizedPostalCode = location.postalCode.trim();

const updateRes = await client.query(
  `UPDATE Location
   SET 
     address = $1,
     county = $2,
     districtId = $3,
     postalCode = $4,
     latitude = $5,
     longitude = $6
   WHERE locationId = $7
   RETURNING *`,
  [
    normalizedAddress,
    normalizedCounty,
    districtId,
    normalizedPostalCode,
    location.latitude ?? null,
    location.longitude ?? null,
    location.locationId,
  ]
);


    client.release();
    return updateRes.rows[0]; 
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
