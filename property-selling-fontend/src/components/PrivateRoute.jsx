import React, { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import AuthContext from "../context/AuthContext";

const PrivateRoute = ({ requiredRole }) => {
  const { user } = useContext(AuthContext);

  console.log("Required Role:", requiredRole);
  console.log("User:", user);

  if (!user) {
    console.log("User not found, redirecting to login...");
    return <Navigate to="/login" replace />;
  }

  // if (requiredRole && user.userType !== requiredRole) {
  //   console.log("User role mismatch, redirecting to home...");
  //   return <Navigate to="/" replace />;
  // }

  return <Outlet />;
};

export default PrivateRoute;
