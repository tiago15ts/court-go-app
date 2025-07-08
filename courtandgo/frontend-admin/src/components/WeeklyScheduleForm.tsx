import { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Paper,
  Checkbox,
  FormControlLabel,
} from "@mui/material";
import { createWeeklySchedule } from "../api/schedule";

const daysOfWeek = [
  { value: "MONDAY", label: "Segunda-feira" },
  { value: "TUESDAY", label: "Terça-feira" },
  { value: "WEDNESDAY", label: "Quarta-feira" },
  { value: "THURSDAY", label: "Quinta-feira" },
  { value: "FRIDAY", label: "Sexta-feira" },
  { value: "SATURDAY", label: "Sábado" },
  { value: "SUNDAY", label: "Domingo" },
];

export function WeeklyScheduleForm({ clubId }: { clubId: number }) {
  const [schedules, setSchedules] = useState(
    daysOfWeek.map((day) => ({
      dayOfWeek: day.value,
      startTime: "",
      endTime: "",
      open: true, // novo campo para indicar se está aberto
    }))
  );

  function handleChange(
    index: number,
    field: "startTime" | "endTime",
    value: string
  ) {
    const updated = [...schedules];
    updated[index][field] = value;
    setSchedules(updated);
  }

  function handleOpenChange(index: number, checked: boolean) {
    const updated = [...schedules];
    updated[index].open = checked;
    // se fechar o dia, limpa os horários
    if (!checked) {
      updated[index].startTime = "";
      updated[index].endTime = "";
    }
    setSchedules(updated);
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    for (const schedule of schedules) {
      await createWeeklySchedule({
        clubId,
        dayOfWeek: schedule.dayOfWeek,
        startTime: schedule.open ? schedule.startTime : null,
        endTime: schedule.open ? schedule.endTime : null,
      });
    }

    alert("Horários adicionados");
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ maxWidth: 600, p: 2, display: "flex", flexDirection: "column", gap: 3 }}
    >
      <Typography variant="h6" mb={1}>Horário Semanal</Typography>

      <Paper sx={{ p: 2 }}>
        <Box sx={{ display: "flex", fontWeight: "bold", mb: 1 }}>
          <Box sx={{ flex: 1 }}>Dia</Box>
          <Box sx={{ flex: 1 }}>Aberto</Box>
          <Box sx={{ flex: 1 }}>Início</Box>
          <Box sx={{ flex: 1 }}>Fim</Box>
        </Box>

        {schedules.map((schedule, index) => (
          <Box
            key={schedule.dayOfWeek}
            sx={{ display: "flex", alignItems: "center", mb: 1 }}
          >
            <Box sx={{ flex: 1 }}>{daysOfWeek[index].label}</Box>

            <Box sx={{ flex: 1 }}>
              <Checkbox
                checked={schedule.open}
                onChange={(e) => handleOpenChange(index, e.target.checked)}
              />
            </Box>

            <Box sx={{ flex: 1 }}>
              <TextField
                type="time"
                value={schedule.startTime}
                onChange={(e) => handleChange(index, "startTime", e.target.value)}
                fullWidth
                inputProps={{ step: 300 }}
                disabled={!schedule.open} // desabilita se não estiver aberto
              />
            </Box>

            <Box sx={{ flex: 1 }}>
              <TextField
                type="time"
                value={schedule.endTime}
                onChange={(e) => handleChange(index, "endTime", e.target.value)}
                fullWidth
                inputProps={{ step: 300 }}
                disabled={!schedule.open} // desabilita se não estiver aberto
              />
            </Box>
          </Box>
        ))}
      </Paper>

      <Button type="submit" variant="contained">
        Guardar Horários
      </Button>
    </Box>
  );
}
