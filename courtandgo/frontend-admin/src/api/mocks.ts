// src/api/mocks.ts

import { fakeClubs } from "./mockData";
import { fakeCourts } from "./mockData";
import { fakeLocations } from "./mockData";

export async function getClubsByOwnerId(ownerId: number) {
  return new Promise<any[]>(resolve => {
    setTimeout(() => resolve(fakeClubs), 300);
  });
}

export async function getClubById(clubId: number) {
  return new Promise<any>((resolve, reject) => {
    setTimeout(() => {
      const club = fakeClubs.find(c => c.clubId === clubId);
      if (club) resolve(club);
      else reject(new Error("Clube não encontrado"));
    }, 300);
  });
}

export async function updateClub(club: any) {
  return new Promise<void>((resolve) => {
    setTimeout(() => {
      console.log("Mock update:", club);
      resolve();
    }, 300);
  });
}

export async function createClub(club: any) {
  return new Promise<any>((resolve) => {
    setTimeout(() => {
      const newClub = { ...club, clubId: Math.floor(Math.random() * 1000) };
      console.log("Mock create:", newClub);
      resolve(newClub);
    }, 300);
  });
}

export async function createCourt(court: any) {
  return new Promise<any>((resolve) => {
    setTimeout(() => {
      const newCourt = { ...court, courtId: Math.floor(Math.random() * 1000) };
      console.log("Mock create court:", newCourt);
      resolve(newCourt);
    }, 300);
  });
}

export async function updateCourt(court: any) {
  return new Promise<void>((resolve) => {
    setTimeout(() => {
      console.log("Mock update court:", court);
      resolve();
    }, 300);
  });
}

export async function createLocation(location: any) {
  return new Promise<any>((resolve) => {
    setTimeout(() => {
      const newLocation = { ...location, locationId: Math.floor(Math.random() * 1000) };
      console.log("Mock create location:", newLocation);
      resolve(newLocation);
    }, 300);
  });
}

export async function updateLocation(location: any) {
  return new Promise<void>((resolve) => {
    setTimeout(() => {
      console.log("Mock update location:", location);
      resolve();
    }, 300);
  });
}

export async function getCourtsByClubId(clubId: number) {
  return new Promise<any[]>((resolve) => {
    setTimeout(() => {
      const courts = fakeCourts.filter(c => c.clubId === clubId);
      resolve(courts);
    }, 300);
  });
}

export async function getLocationsByClubId(clubId: number) {
  return new Promise<any>((resolve, reject) => {
    setTimeout(() => {
      const club = fakeClubs.find(c => c.clubId === clubId);
      if (!club) return reject(new Error("Clube não encontrado"));

      const location = fakeLocations.find(l => l.locationId === club.locationId);
      if (!location) return reject(new Error("Localização não encontrada"));

      resolve(location);
    }, 300);
  });
}
