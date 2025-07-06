// frontend/src/api/clubs.ts
import { VITE_API_URL } from '../config';

export async function getClubsByOwnerId(ownerId: number) {
  const res = await fetch(`${VITE_API_URL}/clubs/owner/${ownerId}`);
  return await res.json();
}

export async function updateClub(club: any) {
  const res = await fetch(`${VITE_API_URL}/clubs`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(club),
  });
  return await res.json();
}

export async function createClub(club: any) {
  const res = await fetch(`${VITE_API_URL}/clubs`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(club),
  });
  return await res.json();
}