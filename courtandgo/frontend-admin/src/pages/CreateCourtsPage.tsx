// src/pages/CreateCourtsPage.tsx
import { useParams } from "react-router-dom";
import { CreateCourtsForm } from "../components/CreateCourtsForm";

export default function CreateCourtsPage() {
  const { clubId } = useParams();

  if (!clubId) return <p>Clube não especificado.</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <CreateCourtsForm clubId={parseInt(clubId)} />
    </div>
  );
}
