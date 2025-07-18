
import { getUserByEmail } from "../../core/queries/user";
import { registerUser } from "../../core/queries/user";


// POST /user/oauthregister
export async function handler(event) {
  const body = JSON.parse(event.body || "{}");
  const { email, name, countryCode, contact } = body;
  
  const userExists = await getUserByEmail(email);
  
  if (userExists !== null && userExists !== undefined) {
    return {
      statusCode: 200,
        headers: {
    "Content-Type": "application/json"
  },
      body: JSON.stringify(userExists),
    };
  }

  // regista user sem password (ou com flag especial)
  const newUser = await registerUser({
      email: email,
      name: name,
      countryId: countryCode,
      phone: contact,
  });

  return {
    statusCode: 201,
      headers: {
    "Content-Type": "application/json"
  },
    body: JSON.stringify(newUser),
  };
}
