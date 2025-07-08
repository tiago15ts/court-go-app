import { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Paper,
  Stack,
} from "@mui/material";

type Court = {
  name: string;
  type: string;
  surfaceType: string;
  capacity: number;
  pricePerHour: number;
};

type CourtField = keyof Court;

type CreateCourtsFormProps = {
  clubId: number; // <- aqui defines a prop que estás a passar
};

export function CreateCourtsForm({ clubId }: CreateCourtsFormProps) {
  const [courts, setCourts] = useState<Court[]>([
    {
      name: "",
      type: "",
      surfaceType: "",
      capacity: 0,
      pricePerHour: 0,
    },
  ]);

  function handleChange(index: number, field: CourtField, value: string) {
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

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    console.log("Enviar courts:", courts);
    // Aqui chamar createCourt para cada court, ou enviar tudo de uma vez conforme a tua API
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ maxWidth: 800, mx: "auto", display: "flex", flexDirection: "column", gap: 3 }}
    >
      <Typography variant="h5">Courts do Clube</Typography>

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
            <TextField
              label="Tipo (Tennis ou Padel)"
              value={court.type}
              onChange={(e) => handleChange(index, "type", e.target.value)}
              fullWidth
              required
            />
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
            <TextField
              label="Preço por hora"
              type="number"
              value={court.pricePerHour}
              onChange={(e) =>
                handleChange(index, "pricePerHour", e.target.value)
              }
              fullWidth
              required
            />
          </Stack>
        </Paper>
      ))}

      <Stack direction="row" spacing={2}>
        <Button variant="outlined" onClick={handleAddCourt}>
          Adicionar Court
        </Button>
        <Button type="submit" variant="contained">
          Guardar Courts
        </Button>
      </Stack>
    </Box>
  );
}
