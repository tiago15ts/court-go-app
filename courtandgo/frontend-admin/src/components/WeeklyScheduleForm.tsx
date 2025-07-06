import { useState } from "react";
import { createWeeklySchedule } from "../api/schedule";

export function WeeklyScheduleForm({ courtId }: { courtId: number }) {
  const [dayOfWeek, setDayOfWeek] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    await createWeeklySchedule({ courtId, dayOfWeek, startTime, endTime });
    alert("Schedule added");
  }

  return (
    <form onSubmit={handleSubmit}>
      <input value={dayOfWeek} onChange={(e) => setDayOfWeek(e.target.value)} placeholder="Day of week" />
      <input value={startTime} onChange={(e) => setStartTime(e.target.value)} placeholder="Start time (HH:mm)" />
      <input value={endTime} onChange={(e) => setEndTime(e.target.value)} placeholder="End time (HH:mm)" />
      <button type="submit">Add</button>
    </form>
  );
}
