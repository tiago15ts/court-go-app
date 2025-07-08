const API_URL = process.env.REACT_APP_API_URL;

export async function getClubsByOwnerId(ownerId: number) {
  const res = await fetch(`${API_URL}/clubs/owner/${ownerId}`);
  return await res.json();
}

export async function updateClub(club: any) {
  const res = await fetch(`${API_URL}/clubs`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(club),
  });
  return await res.json();
}

export async function createClub(club: any) {
  const res = await fetch(`${API_URL}/clubs`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(club),
  });
  return await res.json();
}


export async function getClubById(clubId: number) {
  const res = await fetch(`${API_URL}/clubs/${clubId}`);
  if (!res.ok) {
    throw new Error(`Erro ao obter clube com ID ${clubId}`);
  }
  return await res.json();
}