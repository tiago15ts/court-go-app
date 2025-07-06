import { WeeklyScheduleForm } from "../components/WeeklyScheduleForm";
import { SpecialScheduleForm } from "../components/SpecialScheduleForm";

export default function ScheduleEditor() {
  const courtId = 1; // Replace with actual court ID to manage
  return (
    <div>
      <h2>Weekly Schedule</h2>
      <WeeklyScheduleForm courtId={courtId} />
      <h2>Special Schedule</h2>
      <SpecialScheduleForm courtId={courtId} />
    </div>
  );
}