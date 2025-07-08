// src/api/index.ts
import * as ClubAPI from "./clubs";
import * as CourtAPI from "./courts";
import * as LocationAPI from "./location";
import * as MockAPI from "./mocks";


const isDev = process.env.NODE_ENV === "development";

const Clubs = isDev ? MockAPI : ClubAPI;
const Courts = isDev ? MockAPI : CourtAPI;
const Locations = isDev ? MockAPI : LocationAPI;

// Exporta os métodos de forma unificada
export const getClubsByOwnerId = Clubs.getClubsByOwnerId;
export const createClub = Clubs.createClub;
export const updateClub = Clubs.updateClub;
export const getClubById = Clubs.getClubById;

export const createCourt = Courts.createCourt;
export const updateCourt = Courts.updateCourt;
export const getCourtsByClubId = Courts.getCourtsByClubId;

export const createLocation = Locations.createLocation;
export const updateLocation = Locations.updateLocation;
export const getLocationsByClubId = Locations.getLocationsByClubId;

