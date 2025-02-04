// src/pages/Logout.jsx
import React, { useContext, useEffect } from "react";
import AuthContext from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Logout = () => {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.removeItem("token");
    logout();
    navigate("/login");
  }, [logout, navigate]);

  return <div>Logging out...</div>;
};

export default Logout;
