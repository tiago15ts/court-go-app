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
import { getCourtsByClubId, updateCourt } from "../api/courts";

type Court = {
  id: number;
  name: string;
  sportTypeCourt: string;
  surfaceType: string;
  capacity: number;
  price: number;
  clubId: number;
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
        .then((data) => {
          console.log("Courts carregados:", data); // ← ver aqui se `type` e `pricePerHour` existem
          setCourts(data);
        })
        .catch(() => setError("Erro ao carregar os courts."));
    }
  }, [clubId]);

  function handleChange(index: number, field: keyof Court, value: string) {
    const updated = [...courts];
    updated[index] = {
      ...updated[index],
      [field]:
        field === "capacity" || field === "price"
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
        console.log("Atualizando court:", court);
        await updateCourt({
        courtId: court.id,
        name: court.name,
        type: court.sportTypeCourt,
        surfaceType: court.surfaceType,
        capacity: court.capacity,
        pricePerHour: court.price,
      });
      }
      setSuccess(true);
    } catch (err) {
      console.error("Erro ao guardar alterações:", err);
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
        <Paper key={court.id} sx={{ p: 2 }}>
          <Typography variant="subtitle1">Court {index + 1} : {court.name}</Typography>

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
                value={court.sportTypeCourt}
                onChange={(e) => handleChange(index, "sportTypeCourt", e.target.value)}
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
                value={court.price}
                onChange={(e) => handleChange(index, "price", e.target.value)}
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
