import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getClubById } from "../api";
import { UpdateClubForm } from "../components/UpdateClubForm";
import { CircularProgress, Typography, Box } from "@mui/material";

export default function UpdateClubPage() {
  const { clubId } = useParams<{ clubId: string }>();
  const [club, setClub] = useState<any | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchClub() {
      try {
        const data = await getClubById(Number(clubId));
        setClub(data);
      } catch (err) {
        console.error("Erro ao obter clube:", err);
      } finally {
        setLoading(false);
      }
    }

    fetchClub();
  }, [clubId]);

  if (loading) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", mt: 5 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (!club) {
    return (
      <Typography variant="h6" color="error" align="center" mt={5}>
        Clube não encontrado.
      </Typography>
    );
  }

  return <UpdateClubForm club={club} />;
}
