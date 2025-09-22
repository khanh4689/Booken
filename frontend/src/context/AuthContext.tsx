import React, { createContext, useState, useEffect, type ReactNode } from "react";
import api from "../api/axios";
import { type User } from "../types/Auth";

interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => Promise<void>;
  register: (
    username: string,
    email: string,
    phone: string,
    password: string
  ) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  // Load user từ localStorage khi refresh
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  // Đăng nhập
  const login = async (email: string, password: string) => {
    const res = await api.post<User>("/auth/login", { email, password });
    const data = res.data;
    localStorage.setItem("user", JSON.stringify(data));
    setUser(data);
  };

  // Đăng ký
  const register = async (
    name: string,
    email: string,
    phone: string,
    password: string
  ) => {
    await api.post("/auth/register", {
      name,
      email,
      phone,
      password,
    });
  };

  // Đăng xuất
  const logout = () => {
    localStorage.removeItem("user");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
