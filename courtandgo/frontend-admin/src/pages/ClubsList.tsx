import { useAuth } from "../components/authContext";
import { ClubList } from "../components/ClubList";

export default function ClubsList() {
  const ownerId = useAuth().ownerId;

  if (!ownerId) {
    return <div>A carregar dados do dono...</div>;
  }

  return (
    <div>
      <ClubList ownerId={Number(ownerId)} />
    </div>
  );
}
