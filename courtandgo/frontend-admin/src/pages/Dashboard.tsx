import { Button, Stack, Typography, Box } from "@mui/material";
import { Link as RouterLink, useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();

  function handleLogout() {
    // limpar tokens, estado global, etc?
    // Exemplo: localStorage.removeItem("token");
    navigate("/login");
  }

  return (
    <Box sx={{ maxWidth: 400, mx: "auto", mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        Painel de Administrador
      </Typography>

      <Stack spacing={2}>
        <Button
          variant="contained"
          component={RouterLink}
          to="/clubs/create"
          fullWidth
        >
          Criar Clube
        </Button>

        <Button
          variant="contained"
          component={RouterLink}
          to="/clubs"
          fullWidth
        >
          Editar Clubes
        </Button>

        <Button
          variant="contained"
          component={RouterLink}
          to="/schedules"
          fullWidth
        >
          Editar Horários de Clube
        </Button>

        <Button
          variant="outlined"
          color="error"
          onClick={handleLogout}
          fullWidth
        >
          Terminar Sessão
        </Button>
      </Stack>
    </Box>
  );
}
