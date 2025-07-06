import { useState } from "react";
import { createSpecialSchedule } from "../api/schedule";

export function SpecialScheduleForm({ courtId }: { courtId: number }) {
  const [date, setDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [working, setWorking] = useState(true);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    await createSpecialSchedule({ courtId, date, startTime, endTime, working });
    alert("Special schedule added");
  }

  return (
    <form onSubmit={handleSubmit}>
      <input value={date} onChange={(e) => setDate(e.target.value)} placeholder="Date (YYYY-MM-DD)" />
      <input value={startTime} onChange={(e) => setStartTime(e.target.value)} placeholder="Start time (HH:mm)" />
      <input value={endTime} onChange={(e) => setEndTime(e.target.value)} placeholder="End time (HH:mm)" />
      <label>
        Working:
        <input type="checkbox" checked={working} onChange={() => setWorking(!working)} />
      </label>
      <button type="submit">Add</button>
    </form>
  );
}
