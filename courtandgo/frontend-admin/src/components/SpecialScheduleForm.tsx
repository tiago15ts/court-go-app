import { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Checkbox,
  FormControlLabel,
  Typography,
  Alert,
} from "@mui/material";
import { createSpecialSchedule } from "../api/schedule";

export function SpecialScheduleForm({ clubId }: { clubId: number }) {
  const [date, setDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [working, setWorking] = useState(true);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    // Se estiver fechado, não exigir startTime e endTime
    if (!working) {
      await createSpecialSchedule({ clubId, date, startTime: null, endTime: null, working });
    } else {
      await createSpecialSchedule({ clubId, date, startTime, endTime, working });
    }

    alert("Horário especial adicionado");
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ display: "flex", flexDirection: "column", gap: 2, maxWidth: 400 }}
    >
      <Typography variant="h6">Adicionar Horário Especial</Typography>

      <TextField
        label="Data"
        type="date"
        value={date}
        onChange={(e) => setDate(e.target.value)}
        InputLabelProps={{ shrink: true }}
        required
      />

      <TextField
        label="Hora de Início"
        type="time"
        value={startTime}
        onChange={(e) => setStartTime(e.target.value)}
        InputLabelProps={{ shrink: true }}
        required={working}
        disabled={!working}
      />

      <TextField
        label="Hora de Fim"
        type="time"
        value={endTime}
        onChange={(e) => setEndTime(e.target.value)}
        InputLabelProps={{ shrink: true }}
        required={working}
        disabled={!working}
      />

      <FormControlLabel
        control={
          <Checkbox
            checked={working}
            onChange={(e) => setWorking(e.target.checked)}
          />
        }
        label="Está aberto o clube?"
      />

      {!working && (
        <Alert severity="info" variant="outlined">
          Como o clube está marcado como fechado, só precisa de preencher a data.
        </Alert>
      )}

      <Button variant="contained" type="submit">
        Adicionar
      </Button>
    </Box>
  );
}
