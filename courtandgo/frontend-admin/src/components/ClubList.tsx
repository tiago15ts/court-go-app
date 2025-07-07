import { useEffect, useState } from "react";
import { getClubsByOwnerId } from "../api/clubs";
import {
  List,
  ListItem,
  ListItemText,
  Paper,
  Typography,
  Box,
} from "@mui/material";

export function ClubList({ ownerId }: { ownerId: number }) {
  const [clubs, setClubs] = useState<any[]>([]);

  useEffect(() => {
    getClubsByOwnerId(ownerId).then(setClubs);
  }, [ownerId]);

  return (
    <Box sx={{ maxWidth: 500, marginTop: 3 }}>
      <Typography variant="h6" gutterBottom>
        Lista de Clubes
      </Typography>
      <Paper elevation={2}>
        <List>
          {clubs.map((club) => (
            <ListItem key={club.clubId} divider>
              <ListItemText primary={club.name} />
            </ListItem>
          ))}
        </List>
      </Paper>
    </Box>
  );
}
