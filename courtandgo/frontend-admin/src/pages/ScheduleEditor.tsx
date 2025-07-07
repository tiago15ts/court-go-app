import { WeeklyScheduleForm } from "../components/WeeklyScheduleForm";
import { SpecialScheduleForm } from "../components/SpecialScheduleForm";

export default function ScheduleEditor() {
  const courtId = 1; // Replace with actual court ID to manage
  return (
    <div>
      
      <WeeklyScheduleForm courtId={courtId} />
      
      <SpecialScheduleForm courtId={courtId} />
    </div>
  );
}