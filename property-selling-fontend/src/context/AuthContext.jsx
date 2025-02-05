import React, { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import { API } from "../API";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  const login = async (formData) => {
        try {

      const response = await axios.post(`${API}/user/signin`, formData);      
      const token = response.data.jwt;
      if (!token) throw new Error("No token received from backend");

      localStorage.setItem("user",JSON.stringify(response.data)); // Store token
      setUser(response.data); // Set the user only if the token is valid
      navigate("/");

    } catch (error) {
      console.error("Login failed:", error.response?.data?.message || error.message);
      alert("Login failed: " + (error.response?.data?.message || error.message));
    }

    // console.log("Received Token in Login:", token);
    // localStorage.setItem("token", token); // Store token
    // console.log("Stored Token After Setting:", localStorage.getItem("token")); // Verify storage

    // try {
    //   const decodedUser = jwtDecode(token);
    //   console.log("Decoded User:", decodedUser);

    //   if (!decodedUser.sub) throw new Error("JWT missing email");

    //   setUser(decodedUser); // Set the user only if the token is valid
    //   navigate("/dashboard");
    // } catch (error) {
    //   console.error("Error decoding token:", error);
    //   localStorage.removeItem("token");
    //   setUser(null);
    //   alert("Invalid token. Please try logging in again.");
    // }
  };

  const logout = () => {
    console.log("Logging out...");
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login");
  };

  return (
    <AuthContext.Provider value={{ user, setUser,login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
