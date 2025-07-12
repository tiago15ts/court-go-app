import React, { createContext, useContext, useState, useEffect, ReactNode } from "react";

type AuthContextType = {
  ownerId: string | null;
  token: string | null;
  setOwnerId: (id: string) => void;
  setToken: (token: string) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

type AuthProviderProps = { children: ReactNode };

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [ownerId, setOwnerIdState] = useState<string | null>(null);
  const [token, setTokenState] = useState<string | null>(null);

  useEffect(() => {
    const storedId = localStorage.getItem("ownerId");
    const storedToken = localStorage.getItem("token");
    if (storedId) setOwnerIdState(storedId);
    if (storedToken) setTokenState(storedToken);
  }, []);

  const setOwnerId = (id: string) => {
    localStorage.setItem("ownerId", id);
    setOwnerIdState(id);
  };

  const setToken = (token: string) => {
    localStorage.setItem("token", token);
    setTokenState(token);
  };

  const logout = () => {
    localStorage.removeItem("ownerId");
    localStorage.removeItem("token");
    setOwnerIdState(null);
    setTokenState(null);
  };

  return (
    <AuthContext.Provider value={{ ownerId, token, setOwnerId, setToken, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within an AuthProvider");
  return context;
}
