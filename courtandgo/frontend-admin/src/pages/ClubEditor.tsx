import { useState } from "react";
import { ClubList } from "../components/ClubList";
import { ClubForm } from "../components/ClubForm";

export default function ClubEditor() {
  const [selectedClub, setSelectedClub] = useState<any | null>(null);
  const ownerId = 1; // Replace with actual logged-in owner ID
  
  return (
    <div>
      <h2>Your Clubs</h2>
      <ClubList ownerId={ownerId} />
      {selectedClub && <ClubForm club={selectedClub} />}
    </div>
  );
}