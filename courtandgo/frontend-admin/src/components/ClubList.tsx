import { useEffect, useState } from "react";
import { getClubsByOwnerId } from "../api/clubs";

export function ClubList({ ownerId }: { ownerId: number }) {
  const [clubs, setClubs] = useState<any[]>([]);

  useEffect(() => {
    getClubsByOwnerId(ownerId).then(setClubs);
  }, [ownerId]);

  return (
    <ul>
      {clubs.map((club) => (
        <li key={club.clubId}>{club.name}</li>
      ))}
    </ul>
  );
}
