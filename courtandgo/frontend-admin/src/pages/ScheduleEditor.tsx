import { useParams } from "react-router-dom";
import { WeeklyScheduleForm } from "../components/WeeklyScheduleForm";
import { SpecialScheduleForm } from "../components/SpecialScheduleForm";

export default function ScheduleEditor() {
  const { clubId } = useParams<{ clubId: string }>();

  if (!clubId) return <div>ID do clube não encontrado.</div>;

  return (
    <div>
      <WeeklyScheduleForm clubId={Number(clubId)} />
      <SpecialScheduleForm clubId={Number(clubId)} />
    </div>
  );
}
