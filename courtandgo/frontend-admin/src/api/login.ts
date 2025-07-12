const API_URL = process.env.REACT_APP_API_URL;

export async function loginOwner(email: string, password: string) {
  const res = await fetch(`${API_URL}/owners/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });
  if (!res.ok) {
    throw new Error("Failed to login owner");
  }
  return await res.json();
}