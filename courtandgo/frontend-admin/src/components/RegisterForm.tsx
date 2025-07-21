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
import { createOwner } from "../api/register";
import { useAuth } from "../components/authContext";


export function RegisterForm() {
  const { setOwnerId } = useAuth();
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [contact, setContact] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);
  const [isLoading, setIsLoading] = useState(false);


  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setSuccess(false);
    setIsLoading(true);

    try {
      const result = await createOwner({
        email,
        name,
        phone: contact,
        password,
      });

      setOwnerId(result.ownerid);
      setSuccess(true);

      setTimeout(() => navigate("/dashboard"), 300);
    } catch (err: any) {
      setError(err.message || "Erro no registo. Verifica os dados ou tenta mais tarde.");
    } finally {
    setIsLoading(false);
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
          required
        />

        <TextField
          label="Palavra-passe"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <Button
          type="submit"
          variant="contained"
          disabled={isLoading}
        >
          {isLoading ? "A registar..." : "Registar"}
        </Button>

        <Stack direction="row" justifyContent="center">
          <Typography variant="body2">
            Já tens conta?{" "}
            <Button component={RouterLink} to="/login" size="small">
              Iniciar Sessão
            </Button>
          </Typography>
        </Stack>
      </Box>
    </>
  );
}


