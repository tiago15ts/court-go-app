import { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Paper,
  Stack,
  InputAdornment,
  FormControl,
  InputLabel,
  OutlinedInput,
  Select,
  MenuItem,
} from "@mui/material";
import { createCourt } from "../api/courts";

type Court = {
  courtId: number;
  clubId: number;
  name: string;
  type: string;
  surfaceType: string;
  capacity: number;
  pricePerHour: number;
};

type CreateCourtsFormProps = {
  clubId: number;
};

const sportOptions = [
  { value: "Tennis", label: "Ténis" },
  { value: "Padel", label: "Padel" },
];

export function CreateCourtsForm({ clubId }: CreateCourtsFormProps) {
  const [courts, setCourts] = useState<Omit<Court, "courtId" | "clubId">[]>([
    { name: "", type: "", surfaceType: "", capacity: 0, pricePerHour: 0 },
  ]);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  function handleChange(
    index: number,
    field: keyof Omit<Court, "courtId" | "clubId">,
    value: string
  ) {
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

  function handleAddCourt() {
    setCourts((prev) => [
      ...prev,
      { name: "", type: "", surfaceType: "", capacity: 0, pricePerHour: 0 },
    ]);
  }

 async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setSuccess(false);

    try {
      // Criar todos os courts via API, aguardando um a um (ou usar Promise.all)
      for (const courtData of courts) {
        await createCourt({
          ...courtData,
          clubId,
        });
      }

      setSuccess(true);
      setCourts([{ name: "", type: "", surfaceType: "", capacity: 0, pricePerHour: 0 }]);
    } catch (err: any) {
      setError(err.message || "Erro ao criar courts");
    }
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ maxWidth: 800, mx: "auto", display: "flex", flexDirection: "column", gap: 3 }}
    >
      <Typography variant="h5">Courts do Clube</Typography>
      {success && <Typography color="success.main">Courts criados com sucesso!</Typography>}
      {error && <Typography color="error.main">{error}</Typography>}

      {courts.map((court, index) => (
        <Paper key={index} sx={{ p: 2 }}>
          <Typography variant="subtitle1">Court {index + 1}</Typography>

          <Stack spacing={2} direction="row" mt={2}>
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

          <Stack spacing={2} direction="row" mt={2}>
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
                onChange={(e) =>
                  handleChange(index, "pricePerHour", e.target.value)
                }
                startAdornment={<InputAdornment position="start">€</InputAdornment>}
                inputProps={{
                  step: "0.01",
                  min: "0",
                }}
                required
              />
            </FormControl>
          </Stack>
        </Paper>
      ))}

      <Stack direction="row" spacing={2}>
        <Button variant="outlined" onClick={handleAddCourt}>
          Adicionar Court
        </Button>
        <Button type="submit" variant="contained">
          Criar Courts
        </Button>
      </Stack>
    </Box>
  );
}
