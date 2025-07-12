const API_URL = process.env.REACT_APP_API_URL;

export async function createCourt(court: any) {
  const res = await fetch(`${API_URL}/courts`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(court),
  });
  if (!res.ok) {
    throw new Error("Failed to create court");
  }
  return await res.json();
}

export async function updateCourt(court: any) {
  const res = await fetch(`${API_URL}/courts/${court.courtId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(court),
  });
  if (!res.ok) {
    throw new Error("Failed to update court");
  }
  return await res.json();
}

export async function getCourtsByClubId(clubId: number) {
  const res = await fetch(`${API_URL}/courts/club/${clubId}`);
  if (!res.ok) {
    throw new Error("Failed to fetch courts");
  }
  return await res.json();
}
