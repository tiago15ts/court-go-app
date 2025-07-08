import { ClubList } from "../components/ClubList";

export default function ClubsList() {
  const ownerId = 1; // Substituir futuramente pelo ID do user autenticado

  return (
    <div>
      <ClubList ownerId={ownerId} />
    </div>
  );
}
