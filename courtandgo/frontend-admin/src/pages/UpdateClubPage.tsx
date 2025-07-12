import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getClubById,  } from "../api/clubs";
import { getLocationByClubId } from "../api/location";
import { UpdateClubForm } from "../components/UpdateClubForm";
import { CircularProgress, Typography, Box } from "@mui/material";

export default function UpdateClubPage() {
  const { clubId } = useParams<{ clubId: string }>();
  const [club, setClub] = useState<any | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchClubAndLocation() {
      try {
        const clubData = await getClubById(Number(clubId));

        // carrega a localização correta com base no locationId
        const locationData = await getLocationByClubId(clubData.locationId);

        // junta a localização ao clube antes de passar ao form
        setClub({
          ...clubData,
          location: locationData,
        });
      } catch (err) {
        console.error("Erro ao obter clube ou localização:", err);
      } finally {
        setLoading(false);
      }
    }

    fetchClubAndLocation();
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
