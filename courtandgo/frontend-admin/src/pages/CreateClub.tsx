import { useState } from "react";
import { CreateClubForm } from "../components/CreateClubForm";

export default function CreateClub() {

  return (
    <div>
      <h2>Criar Novo Clube</h2>
      <CreateClubForm />
    </div>
  );
}
