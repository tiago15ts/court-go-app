import { useState } from "react";
import { updateClub, updateLocation } from "../api";
import {
  TextField,
  Button,
  Box,
  MenuItem,
  Typography,
  Alert,
} from "@mui/material";

const sportOptions = [
  { value: "TENNIS", label: "Ténis" },
  { value: "PADEL", label: "Padel" },
  { value: "BOTH", label: "Ambos" },
];

export function UpdateClubForm({ club }: { club: any }) {
  const [name, setName] = useState(club.name);
  const [sport, setSport] = useState(club.sports || "TENNIS");
  const [numCourts, setNumCourts] = useState(club.nrOfCourts || 1);

  // localização
  const [address, setAddress] = useState(club.location?.address || "");
  const [county, setCounty] = useState(club.location?.county || "");
  const [district, setDistrict] = useState(club.location?.district || "");
  const [postalCode, setPostalCode] = useState(club.location?.postalCode || "");

  const [success, setSuccess] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setSuccess(false);

    try {
      // 1. Atualiza localização
      await updateLocation({
        locationId: club.location.locationId,
        address,
        county,
        district,
        postalCode,
      });

      // 2. Atualiza clube
      await updateClub({
        clubId: club.clubId,
        name,
        sports: sport,
        nrOfCourts: numCourts,
        locationId: club.location.locationId, // mantém o mesmo
      });

      setSuccess(true);
    } catch (err) {
      setError("Erro ao atualizar clube ou localização.");
    }
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ maxWidth: 600, mx: "auto", display: "flex", flexDirection: "column", gap: 2 }}
    >
      <Typography variant="h6">Editar Clube</Typography>

      {success && <Alert severity="success">Alterações guardadas com sucesso!</Alert>}
      {error && <Alert severity="error">{error}</Alert>}

      <TextField
        label="Nome do Clube"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
        fullWidth
      />

      <TextField
        select
        label="Desporto"
        value={sport}
        onChange={(e) => setSport(e.target.value)}
        required
      >
        {sportOptions.map((option) => (
          <MenuItem key={option.value} value={option.value}>
            {option.label}
          </MenuItem>
        ))}
      </TextField>

      <TextField
        label="Número de Courts"
        type="number"
        inputProps={{ min: 1 }}
        value={numCourts}
        onChange={(e) => setNumCourts(parseInt(e.target.value))}
        required
      />

      <Typography variant="h6" mt={3}>
        Localização
      </Typography>

      <TextField
        label="Morada"
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        required
      />

      <TextField
        label="Concelho"
        value={county}
        onChange={(e) => setCounty(e.target.value)}
        required
      />

      <TextField
        label="Distrito"
        value={district}
        onChange={(e) => setDistrict(e.target.value)}
        required
      />

      <TextField
        label="Código Postal"
        value={postalCode}
        onChange={(e) => setPostalCode(e.target.value)}
        required
      />

      <Button type="submit" variant="contained">
        Guardar Alterações
      </Button>
    </Box>
  );
}
