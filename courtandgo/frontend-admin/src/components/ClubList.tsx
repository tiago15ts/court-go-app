import { useEffect, useState } from "react";
import { getClubsByOwnerId } from "../api/clubs";
import {
  List,
  ListItem,
  ListItemText,
  Paper,
  Typography,
  Box,
  Button,
  Stack,
} from "@mui/material";
import { useNavigate } from "react-router-dom";

export function ClubList({ ownerId }: { ownerId: number | null }) {
  const [clubs, setClubs] = useState<any[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    if (!ownerId) return;

    getClubsByOwnerId(ownerId)
      .then((data) => {
        setClubs(data);
        console.log("Clubs loaded:", data); // aqui sim já tens os dados corretos
      })
      .catch((error) => {
        console.error("Erro ao carregar clubes:", error);
      });
  }, [ownerId]);

  return (
    <Box sx={{ maxWidth: 800, marginTop: 3 }}>
      <Typography variant="h6" gutterBottom>
        Lista de Clubes
      </Typography>
      <Paper elevation={2}>
        <List>
          {clubs.map((club) => (
            <ListItem
              key={club.clubId}
              divider
              sx={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}
            >
              <ListItemText primary={club.name} />
              <Stack direction="row" spacing={1}>
                <Button
                  variant="outlined"
                  size="small"
                  onClick={() => navigate(`/clubs/edit/${club.id}`)}
                >
                  Editar Clube
                </Button>
                <Button
                  variant="outlined"
                  size="small"
                  onClick={() => navigate(`/clubs/${club.id}/edit-courts`)}
                >
                  Editar Courts
                </Button>
                <Button
                  variant="contained"
                  size="small"
                  onClick={() => navigate(`/clubs/${club.id}/schedules`)}
                >
                  Editar Horários
                </Button>
                <Button
                  variant="outlined"
                  size="small"
                  onClick={() => navigate(`/clubs/${club.id}/create-courts`)}
                >
                  Adicionar Courts
                </Button>
              </Stack>
            </ListItem>
          ))}
        </List>
      </Paper>
    </Box>
  );
}
