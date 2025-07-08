import { useEffect, useState } from "react";
import { getClubsByOwnerId } from "../api";
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

export function ClubList({ ownerId }: { ownerId: number }) {
  const [clubs, setClubs] = useState<any[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    getClubsByOwnerId(ownerId).then(setClubs);
  }, [ownerId]);

  return (
    <Box sx={{ maxWidth: 600, marginTop: 3 }}>
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
                  onClick={() => navigate(`/clubs/edit/${club.clubId}`)}
                >
                  Editar Clube
                </Button>
                <Button
                  variant="outlined"
                  size="small"
                  onClick={() => navigate(`/clubs/${club.clubId}/edit-courts`)}
                >
                  Editar Courts
                </Button>
                <Button
                  variant="contained"
                  size="small"
                  onClick={() => navigate(`/clubs/${club.clubId}/schedules`)}
                >
                  Editar Horários
                </Button>
              </Stack>
            </ListItem>
          ))}
        </List>
      </Paper>
    </Box>
  );
}
