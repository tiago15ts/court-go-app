import {
  CognitoUserPool,
  CognitoUser,
  AuthenticationDetails,
} from "amazon-cognito-identity-js";

const poolData = {
  UserPoolId: "eu-west-3_6c1AJ3Oez",
  ClientId: "1fh5i2j79qsdbqihk2lmo0q6q6",
};

const userPool = new CognitoUserPool(poolData);

export function signInUser({
  email,
  password,
}: {
  email: string;
  password: string;
}) {
  return new Promise((resolve, reject) => {
    const user = new CognitoUser({
      Username: email,
      Pool: userPool,
    });

    const authDetails = new AuthenticationDetails({
      Username: email,
      Password: password,
    });

    user.authenticateUser(authDetails, {
      onSuccess: (result) => {
        const accessToken = result.getAccessToken().getJwtToken();
        const idToken = result.getIdToken().getJwtToken();
        const refreshToken = result.getRefreshToken().getToken();

        resolve({
          accessToken,
          idToken,
          refreshToken,
        });
      },
      onFailure: (err) => {
        reject(err);
      },
    });
  });
}
