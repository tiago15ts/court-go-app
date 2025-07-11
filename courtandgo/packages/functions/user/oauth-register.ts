
import { getUserByEmail } from "../../core/queries/user";
import { registerUser } from "../../core/queries/user";


// POST /auth/oauth-register
export async function handler(event) {
  const body = JSON.parse(event.body || "{}");
  const { email, name, countryCode, contact } = body;

  // verifica se user já existe na base
  const userExists = await getUserByEmail(email);
  if(userExists) {
    return {
      statusCode: 409,
      body: JSON.stringify({ error: "User already exists" }),
    };
  }

  // regista user sem password (ou com flag especial)
  const newUser = await registerUser({
    email,
    name,
    countryId: countryCode,
    phone: contact,
  });

  return {
    statusCode: 201,
    body: JSON.stringify(newUser),
  };
}
