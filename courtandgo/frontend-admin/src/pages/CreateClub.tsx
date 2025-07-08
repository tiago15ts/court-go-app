import { useState } from "react";
import { CreateClubForm } from "../components/CreateClubForm";
import { CreateCourtsForm } from "../components/CreateCourtsForm";
import { Box, Typography } from "@mui/material";

export default function CreateClubPage() {
  const [createdClubId, setCreatedClubId] = useState<number | null>(null);

  return (
    <Box sx={{ maxWidth: 800, mx: "auto", mt: 4 }}>
      <Typography variant="h4" gutterBottom>Criar Clube e Courts</Typography>

      <CreateClubForm onClubCreated={setCreatedClubId} />

      
        <CreateCourtsForm clubId={1} />
      
    </Box>
  );
}
