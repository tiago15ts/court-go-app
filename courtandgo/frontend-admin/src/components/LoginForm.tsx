import { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  Stack,
} from "@mui/material";
import { useNavigate, Link as RouterLink } from "react-router-dom";
import { loginOwner } from "../api/login";
import { useAuth } from "./authContext";

export function LoginForm() {
  const { setOwnerId } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

async function handleSubmit(e: React.FormEvent) {
  e.preventDefault();
  setError(null);

  try {
    const ownerData = await loginOwner(email, password);

    setOwnerId(ownerData.ownerId);
    //setToken(ownerData.token);

    console.log("Login bem sucedido:", ownerData);

    navigate("/dashboard");
  } catch (err: any) {
    setError(err.message || "Email ou palavra-passe inválidos.");
  }
}


  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{
        maxWidth: 400,
        mx: "auto",
        mt: 4,
        display: "flex",
        flexDirection: "column",
        gap: 2,
      }}
    >
      <Typography variant="h5">Iniciar Sessão</Typography>

      {error && <Alert severity="error">{error}</Alert>}

      <TextField
        label="Email"
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />

      <TextField
        label="Palavra-passe"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      />

      <Button type="submit" variant="contained">
        Entrar
      </Button>

      <Stack direction="row" justifyContent="center">
        <Typography variant="body2">
          Ainda não tens conta?{" "}
          <Button component={RouterLink} to="/register" size="small">
            Registar
          </Button>
        </Typography>
      </Stack>
    </Box>
  );
}
