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

export function RegisterForm() {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [contact, setContact] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setSuccess(false);

    try {
      const res = await fetch("/api/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, name, password, contact }),
      });

      if (!res.ok) throw new Error("Erro ao registar");

      setSuccess(true);
      setTimeout(() => navigate("/dashboard"), 1500);
    } catch (err) {
      setError("Erro no registo. Verifica os dados ou tenta mais tarde.");
    }
  }

  return (
    <>
    <Typography variant="h5">
    Bem vindo ao painel de donos de clubes da plataforma Court&Go
  </Typography>
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


      <Typography variant="h5">Criar Conta</Typography>

      {error && <Alert severity="error">{error}</Alert>}
      {success && (
        <Alert severity="success">Registo com sucesso! A redirecionar...</Alert>
      )}

      <TextField
        label="Email"
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />

      <TextField
        label="Nome"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
      />

      <TextField
        label="Telemóvel"
        type="tel"
        value={contact}
        onChange={(e) => setContact(e.target.value)}
        inputProps={{ inputMode: "numeric", pattern: "[0-9]*" }}
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
        Registar
      </Button>

      <Stack direction="row" justifyContent="center">
        <Typography variant="body2">
          Já tens conta?{" "}
          <Button component={RouterLink} to="/login" size="small">
            Iniciar Sessão
          </Button>
        </Typography>
      </Stack>

      {process.env.NODE_ENV === "development" && (
        <Button
          variant="outlined"
          color="secondary"
          onClick={() => navigate("/dashboard")}
        >
          Aceder diretamente ao Dashboard (Dev)
        </Button>
      )}
    </Box>
    </>
  );
}
