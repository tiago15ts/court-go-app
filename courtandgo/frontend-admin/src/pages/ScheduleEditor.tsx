import { WeeklyScheduleForm } from "../components/WeeklyScheduleForm";
import { SpecialScheduleForm } from "../components/SpecialScheduleForm";

export default function ScheduleEditor() {
  const clubId = 1; // Replace with actual court ID to manage
  return (
    <div>
      
      <WeeklyScheduleForm clubId={clubId} />

      <SpecialScheduleForm clubId={clubId} />
    </div>
  );
}