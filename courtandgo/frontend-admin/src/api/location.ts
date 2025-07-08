const API_URL = process.env.REACT_APP_API_URL;

export async function createLocation(location: any) {
  const res = await fetch(`${API_URL}/locations`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(location),
  });
  if (!res.ok) {
    throw new Error("Failed to create location");
  }
  return await res.json();
}

export async function updateLocation(location: any) {
  const res = await fetch(`${API_URL}/locations/${location.locationId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(location),
  });
  if (!res.ok) {
    throw new Error("Failed to update location");
  }
  return await res.json();
}

export async function getLocationsByClubId(clubId: number) {
  const res = await fetch(`${API_URL}/clubs/${clubId}/locations`);
  if (!res.ok) {
    throw new Error("Failed to fetch locations");
  }
  return await res.json();
}