import { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
} from "@mui/material";
import { useAuth } from "./authContext";
import { useNavigate } from "react-router-dom";
import { createLocation } from "../api/location";
import { createClub } from "../api/clubs";


const sportOptions = [
  { value: "Tennis", label: "Ténis" },
  { value: "Padel", label: "Padel" },
  { value: "Both", label: "Ambos" },
];

export function CreateClubForm({ onClubCreated }: { onClubCreated: (clubId: number) => void }) {
  const [name, setName] = useState("");
  const [sport, setSport] = useState("Tennis");
  const [numCourts, setNumCourts] = useState(1);

  // Campos de localização
  const [address, setAddress] = useState("");
  const [county, setCounty] = useState("");
  const [district, setDistrict] = useState("");
  const [postalCode, setPostalCode] = useState("");

  const [success, setSuccess] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const { ownerId } = useAuth();

async function handleSubmit(e: React.FormEvent) {
  e.preventDefault();
  setError(null);
  setSuccess(false);

  if (!ownerId) {
    setError("Precisa de estar autenticado para criar um clube.");
    return;
  }

  try {
    // 1. Criar localização real
    const location = await createLocation({
      address,
      county,
      district,
      postalCode,
    });

    // 2. Criar clube real associado à localização e ao owner autenticado
    const club = await createClub({
      name,
      sports: sport,
      nrOfCourts: numCourts,
      ownerId,
      locationId: location.locationId,
    });

    setSuccess(true);
    onClubCreated(club.clubId);

    // Limpar campos
    setName("");
    setSport("Tennis");
    setNumCourts(1);
    setAddress("");
    setCounty("");
    setDistrict("");
    setPostalCode("");

    setTimeout(() => navigate("/clubs"), 300);
  } catch (err: any) {
    console.error(err);
    setError(err.message || "Erro ao criar clube ou localização.");
  }
}

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
      <Typography variant="h6" gutterBottom>Criar Clube</Typography>
      {success && <Alert severity="success">Clube criado com sucesso!</Alert>}
      {error && <Alert severity="error">{error}</Alert>}

      <TextField
        label="Nome"
        value={name}
        onChange={(e) => setName(e.target.value)}
        fullWidth required sx={{ mb: 2 }}
      />

      <FormControl fullWidth required sx={{ mb: 2 }}>
        <InputLabel>Desporto</InputLabel>
        <Select
          value={sport}
          label="Desporto"
          onChange={(e) => setSport(e.target.value)}
        >
          {sportOptions.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.label}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <TextField
        label="Número de Courts"
        type="number"
        value={numCourts}
        onChange={(e) => setNumCourts(Number(e.target.value))}
        fullWidth required sx={{ mb: 2 }}
      />

      {/* Campos da localização */}
      <TextField
        label="Morada"
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        fullWidth required sx={{ mb: 2 }}
      />
      <TextField
        label="Concelho"
        value={county}
        onChange={(e) => setCounty(e.target.value)}
        fullWidth required sx={{ mb: 2 }}
      />
      <TextField
        label="Distrito"
        value={district}
        onChange={(e) => setDistrict(e.target.value)}
        fullWidth required sx={{ mb: 2 }}
      />
      <TextField
        label="Código Postal"
        value={postalCode}
        onChange={(e) => setPostalCode(e.target.value)}
        fullWidth required sx={{ mb: 2 }}
      />

      <Button type="submit" variant="contained">Criar Clube</Button>
    </Box>
  );
}
