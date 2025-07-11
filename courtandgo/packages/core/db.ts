// packages/core/db.ts
import postgres from "postgres";
import { Resource } from "sst";
import { Pool } from "pg";


export const db = new Pool({
  user: Resource.CourtAndGoDB.username,
  password: Resource.CourtAndGoDB.password,
  database: Resource.CourtAndGoDB.database,
  host: Resource.CourtAndGoDB.host,
  port: Resource.CourtAndGoDB.port,
});

/*
import pg from "pg";
import { Resource } from "sst";

const { Client } = pg;
const db = new Client({
  user: Resource.CourtAndGoDB.username,
  password: Resource.CourtAndGoDB.password,
  database: Resource.CourtAndGoDB.database,
  host: Resource.CourtAndGoDB.host,
  port: Resource.CourtAndGoDB.port,
});
await db.connect();
*/