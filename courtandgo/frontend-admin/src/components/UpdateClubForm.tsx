import { useState, useEffect } from "react";
import { updateClub} from "../api/clubs";
import { updateLocation } from "../api/location";
import {
  TextField,
  Button,
  Box,
  MenuItem,
  Typography,
  Alert,
} from "@mui/material";


const sportOptions = [
  { value: "Tennis", label: "Ténis" },
  { value: "Padel", label: "Padel" },
  { value: "Both", label: "Ambos" },
];

export function UpdateClubForm({ club }: { club: any }) {
  const [name, setName] = useState(club.name);
  const [sport, setSport] = useState(club.sportsClub || "Tennis");
  const [numCourts, setNumCourts] = useState(club.nrOfCourts || 1);

  // localização
  const [address, setAddress] = useState(club.location?.address || "");
  const [county, setCounty] = useState(club.location?.county || "");
  const [district, setDistrict] = useState(club.location?.district.name || "");
  const [postalCode, setPostalCode] = useState(club.location?.postalCode || "");

  const [success, setSuccess] = useState(false);
  const [error, setError] = useState<string | null>(null);
  //console.log("UpdateClubForm props:", club);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setSuccess(false);

    try {
      
      const locationUpdateResponse = await updateLocation({
        locationId: club.location.id,
        address: address,
        county: county,
        district: district,
        postalCode: postalCode,
      });
      if (!locationUpdateResponse) {
        throw new Error("Erro ao atualizar localização.");
      } 
      console.log("Localização atualizada", locationUpdateResponse);

      
      const clubUpdateResponse = await updateClub({
        clubId: club.id,
        name: name,
        sports: sport,
        nrOfCourts: numCourts,
        locationId: club.location.id,
      });
      if (!clubUpdateResponse) {
        throw new Error("Erro ao atualizar clube.");
      }
      console.log("Clube atualizado", clubUpdateResponse);

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
