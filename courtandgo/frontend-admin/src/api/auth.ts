/*
const API_URL = process.env.REACT_APP_API_URL;

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
    const err = await res.json();
    throw new Error(err.error || "Erro ao registar owner");
  }

  return await res.json();
}
*/

import {
  CognitoUserPool,
  CognitoUserAttribute,
} from "amazon-cognito-identity-js";

const poolData = {
  UserPoolId: "eu-west-3_6c1AJ3Oez",
  ClientId: "1fh5i2j79qsdbqihk2lmo0q6q6",
};

const userPool = new CognitoUserPool(poolData);

export function signUpUser({ email, password, name, phone }: { email: string; password: string; name: string; phone: string; }) {
  return new Promise((resolve, reject) => {
    const attributeList = [
      new CognitoUserAttribute({ Name: "email", Value: email }),
      new CognitoUserAttribute({ Name: "name", Value: name }),
      new CognitoUserAttribute({ Name: "phone_number", Value: phone }),
    ];

    userPool.signUp(email, password, attributeList, [], (err, result) => {
      
      if (err) {
        reject(err);
      } else {
        resolve(result); // contém user info e sessão de confirmação
      }
    });
  });
}






