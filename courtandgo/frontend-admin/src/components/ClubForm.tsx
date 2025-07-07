import { useState } from "react";
import { updateClub } from "../api/clubs";
import { TextField, Button, Box } from "@mui/material";

export function ClubForm({ club }: { club: any }) {
  const [name, setName] = useState(club.name);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    await updateClub({ ...club, name });
    alert("Club updated");
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ display: "flex", flexDirection: "column", gap: 2, maxWidth: 400 }}
    >
      <TextField
        label="Club Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        variant="outlined"
        required
      />
      <Button type="submit" variant="contained" color="primary">
        Save
      </Button>
    </Box>
  );
}
