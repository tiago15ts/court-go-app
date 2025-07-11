import React, { createContext, useContext, useState, useEffect, ReactNode } from "react";

type AuthContextType = {
  ownerId: string | null;
  setOwnerId: (id: string) => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

type AuthProviderProps = {
  children: ReactNode;
};

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [ownerId, setOwnerIdState] = useState<string | null>(null);

  // Lê o valor do localStorage ao iniciar
  useEffect(() => {
    const storedId = localStorage.getItem("ownerId");
    if (storedId) {
      setOwnerIdState(storedId);
    }
  }, []);

  const setOwnerId = (id: string) => {
    localStorage.setItem("ownerId", id);
    setOwnerIdState(id);
  };

  return (
    <AuthContext.Provider value={{ ownerId, setOwnerId }}>
      {children}
    </AuthContext.Provider>
  );
};

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
