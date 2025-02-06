import React, { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { API } from "../API";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem("user");
    return storedUser ? JSON.parse(storedUser) : null;
  });

  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser));
      } catch (error) {
        console.error("Error parsing stored user:", error);
        localStorage.removeItem("user");
      }
    }
  }, []);

  const login = async (formData) => {
    try {
      const response = await axios.post(`${API}/user/signin`, formData);
      if (!response.data.jwt) throw new Error("No token received from backend");

      localStorage.setItem("user", JSON.stringify(response.data));
      setUser(response.data);
      console.log("User set after login:", response.data);
      navigate("/");
    } catch (error) {
      console.error("Login failed:", error.response?.data?.message || error.message);
      alert("Login failed: " + (error.response?.data?.message || error.message));
    }
  };

  const logout = () => {
    console.log("Logging out...");
    localStorage.removeItem("user");
    setUser(null);
    navigate("/");
  };

  return <AuthContext.Provider value={{ user, setUser, login, logout }}>{children}</AuthContext.Provider>;
};

export default AuthContext;
