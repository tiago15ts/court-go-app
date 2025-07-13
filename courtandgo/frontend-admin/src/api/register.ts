
const API_URL = process.env.REACT_APP_API_URL;

//register a new owner
export async function createOwner(owner: {
  email: string;
  name: string;
  phone: string;
  password: string;
}) {
  const res = await fetch(`${API_URL}/owners/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(owner),
  });

  if (!res.ok) {
    let errorMessage = "Erro ao registar owner";
    try {
      const err = await res.json();
      errorMessage = err.error || errorMessage;
    } catch (_) {
      // ignora erro de parsing
    }
    throw new Error(errorMessage);
  }
  
 const responseText = await res.text();             // ← Lê como texto
  const parsed = JSON.parse(responseText);           // ← Converte string JSON em objeto
  return parsed.owner;  
}

