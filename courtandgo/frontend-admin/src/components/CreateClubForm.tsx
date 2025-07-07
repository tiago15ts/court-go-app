import { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { createClub } from "../api/clubs";

type SportType = "TENIS" | "PADEL" | "AMBOS";

export function CreateClubForm() {
  const [name, setName] = useState("");
  const [sport, setSport] = useState<SportType>("TENIS");
  const [numCourts, setNumCourts] = useState<number | "">("");
  const [address, setAddress] = useState("");
  const [county, setCounty] = useState("");
  const [district, setDistrict] = useState("");
  const [postalCode, setPostalCode] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  function isValidNumber(value: number | ""): value is number {
    return typeof value === "number" && !isNaN(value) && value > 0;
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setSuccess(false);

    if (!name.trim()) {
      setError("O nome do clube é obrigatório.");
      return;
    }

    if (!isValidNumber(numCourts)) {
      setError("Número de courts deve ser um número positivo.");
      return;
    }

    if (!address.trim()) {
      setError("A morada é obrigatória.");
      return;
    }

    if (!county.trim()) {
      setError("O concelho é obrigatório.");
      return;
    }

    if (!district.trim()) {
      setError("O distrito é obrigatório.");
      return;
    }

    if (!postalCode.trim()) {
      setError("O código postal é obrigatório.");
      return;
    }

    try {
      const newClub = {
        name,
        sport,
        numCourts,
        address,
        county,
        district,
        postalCode,
      };

      const res = await createClub(newClub);

      if (res && res.clubId) {
        setSuccess(true);
        setName("");
        setSport("TENIS");
        setNumCourts("");
        setAddress("");
        setCounty("");
        setDistrict("");
        setPostalCode("");
      } else {
        setError("Erro ao criar o clube.");
      }
    } catch (err) {
      setError("Erro ao comunicar com o servidor.");
    }
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ maxWidth: 500, mx: "auto", p: 3, display: "flex", flexDirection: "column", gap: 2 }}
    >
      <Typography variant="h5" mb={2}>
        Criar Clube
      </Typography>

      {error && <Alert severity="error">{error}</Alert>}
      {success && <Alert severity="success">Clube criado com sucesso!</Alert>}

      <TextField
        label="Nome do Clube"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
        fullWidth
      />

      <FormControl fullWidth required>
        <InputLabel>Desporto</InputLabel>
        <Select
          value={sport}
          label="Desporto"
          onChange={(e) => setSport(e.target.value as SportType)}
        >
          <MenuItem value="TENNIS">Ténis</MenuItem>
          <MenuItem value="PADEL">Padel</MenuItem>
          <MenuItem value="BOTH">Ambos</MenuItem>
        </Select>
      </FormControl>

      <TextField
        label="Número de Courts"
        type="number"
        inputProps={{ min: 1 }}
        value={numCourts}
        onChange={(e) => {
          const val = e.target.value;
          setNumCourts(val === "" ? "" : Number(val));
        }}
        required
        fullWidth
      />

      <TextField
        label="Morada"
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        required
        fullWidth
      />

      <TextField
        label="Concelho"
        value={county}
        onChange={(e) => setCounty(e.target.value)}
        required
        fullWidth
      />

      <TextField
        label="Distrito"
        value={district}
        onChange={(e) => setDistrict(e.target.value)}
        required
        fullWidth
      />

      <TextField
        label="Código Postal"
        value={postalCode}
        onChange={(e) => setPostalCode(e.target.value)}
        required
        fullWidth
      />

      <Button type="submit" variant="contained" size="large">
        Criar
      </Button>
    </Box>
  );
}
