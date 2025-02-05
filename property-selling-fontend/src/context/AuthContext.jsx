import React, { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { API } from "../API";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  // useEffect(() => {
  //   const storedUser = localStorage.getItem("user");
  //   if (storedUser) {
  //     setUser(JSON.parse(storedUser));
  //   }
  // }, []);

  const login = async (formData) => {
    try {
      const response = await axios.post(`${API}/user/signin`, formData);
      // const { jwt, user } = response.data;

      if (!response.data.jwt) throw new Error("No token received from backend");

      // localStorage.setItem("token", jwt); // Store only token
      localStorage.setItem("user", JSON.stringify(response.data)); // Store user separately
      setUser(response.data); // Set user state
      navigate("/");
    } catch (error) {
      console.error("Login failed:", error.response?.data?.message || error.message);
      alert("Login failed: " + (error.response?.data?.message || error.message));
    }
  };

  const logout = () => {
    console.log("Logging out...");
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    setUser(null);
    navigate("/login");
  };

  return (
    <AuthContext.Provider value={{ user, setUser, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
