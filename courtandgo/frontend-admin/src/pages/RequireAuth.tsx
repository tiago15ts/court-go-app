import { Navigate } from "react-router-dom";
import { useAuth } from "../components/authContext";
import * as React from "react";

export function RequireAuth({ children }: { children: React.ReactNode }) {
  const { ownerId } = useAuth();

  if (!ownerId) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}
