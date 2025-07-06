import { useState } from "react";
import { updateClub } from "../api/clubs";

export function ClubForm({ club }: { club: any }) {
  const [name, setName] = useState(club.name);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    await updateClub({ ...club, name });
    alert("Club updated");
  }

  return (
    <form onSubmit={handleSubmit}>
      <input value={name} onChange={(e) => setName(e.target.value)} />
      <button type="submit">Save</button>
    </form>
  );
}
