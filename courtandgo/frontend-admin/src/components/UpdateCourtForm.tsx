import { useEffect, useState } from "react";
import {
  Box,
  Typography,
  TextField,
  Button,
  Paper,
  Stack,
  Alert,
  InputAdornment,
  FormControl,
  InputLabel,
  OutlinedInput,
  Select,
  MenuItem,
} from "@mui/material";
import { useParams } from "react-router-dom";
import { getCourtsByClubId, updateCourt } from "../api";

type Court = {
  courtId: number;
  name: string;
  type: string;
  surfaceType: string;
  capacity: number;
  pricePerHour: number;
};

const sportOptions = [
  { value: "Tennis", label: "Ténis" },
  { value: "Padel", label: "Padel" },
];


export function UpdateCourtForm() {
  const { clubId } = useParams<{ clubId: string }>();
  const [courts, setCourts] = useState<Court[]>([]);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (clubId) {
      getCourtsByClubId(Number(clubId))
        .then(setCourts)
        .catch(() => setError("Erro ao carregar os courts."));
    }
  }, [clubId]);

  function handleChange(index: number, field: keyof Court, value: string) {
    const updated = [...courts];
    updated[index] = {
      ...updated[index],
      [field]:
        field === "capacity" || field === "pricePerHour"
          ? Number(value)
          : value,
    };
    setCourts(updated);
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setSuccess(false);
    setError(null);
    try {
      for (const court of courts) {
        await updateCourt(court);
      }
      setSuccess(true);
    } catch {
      setError("Erro ao guardar alterações.");
    }
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ maxWidth: 800, mx: "auto", mt: 4, display: "flex", flexDirection: "column", gap: 3 }}
    >
      <Typography variant="h5">Editar Courts do Clube</Typography>

      {error && <Alert severity="error">{error}</Alert>}
      {success && <Alert severity="success">Courts atualizados com sucesso!</Alert>}

      {courts.map((court, index) => (
        <Paper key={court.courtId} sx={{ p: 2 }}>
          <Typography variant="subtitle1">Court {index + 1}</Typography>

          <Stack direction="row" spacing={2} mt={2}>
            <TextField
              label="Nome"
              value={court.name}
              onChange={(e) => handleChange(index, "name", e.target.value)}
              fullWidth
              required
            />
            <FormControl fullWidth required>
  <InputLabel>Tipo</InputLabel>
  <Select
    label="Tipo"
    value={court.type}
    onChange={(e) => handleChange(index, "type", e.target.value)}
  >
    {sportOptions.map((option) => (
      <MenuItem key={option.value} value={option.value}>
        {option.label}
      </MenuItem>
    ))}
  </Select>
</FormControl>

          </Stack>

          <Stack direction="row" spacing={2} mt={2}>
            <TextField
              label="Tipo de Piso"
              value={court.surfaceType}
              onChange={(e) =>
                handleChange(index, "surfaceType", e.target.value)
              }
              fullWidth
            />
            <TextField
              label="Capacidade"
              type="number"
              value={court.capacity}
              onChange={(e) => handleChange(index, "capacity", e.target.value)}
              fullWidth
              required
            />
            
            <FormControl fullWidth sx={{ m: 1 }}>
              <InputLabel>Preço por hora</InputLabel>
              <OutlinedInput

                type="number"
                inputMode="decimal"
                label="Preço por hora"
                value={court.pricePerHour}
                onChange={(e) => handleChange(index, "pricePerHour", e.target.value)}
                startAdornment={<InputAdornment position="start">€</InputAdornment>}
                inputProps={{
                  step: "0.01",
                  min: "0"
                }}
              />
            </FormControl>
          </Stack>
        </Paper>
      ))}

      <Button type="submit" variant="contained" sx={{ alignSelf: "flex-start" }}>
        Guardar Alterações
      </Button>
    </Box>
  );
}
