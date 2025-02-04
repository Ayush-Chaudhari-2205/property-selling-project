// src/App.jsx
import React, { useContext, useEffect } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Profile from "./pages/Profile";
import Logout from "./pages/Logout";
import AddProperty from "./pages/seller/AddProperty";
import ListProperties from "./pages/seller/ListProperties";
import RootLayout from "./Layout/RootLayout";
import Home from "./pages/Home";
import NotFound from "./pages/NotFound";
import ContactUs from "./pages/ContactUs";
import DashboardLayout from "./Layout/DashboardLayout";
import Dashboard from "./pages/Dashboard";
import AuthContext from "./context/AuthContext";
import { useState } from 'react';

const App = () => {
  const { user } = useContext(AuthContext);

  // const [Users, setUsers] = useState(null);

  // useEffect(() => {
  //   setUsers(user);

  // }, [])
  

  return (
    <Routes>
      <Route path="/" element={<RootLayout />}>
        <Route index element={<Home />} />
        <Route path="/contact-us" element={<ContactUs />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
      </Route>

      {user ? ( 
          <Route path="/dashboard" element={<DashboardLayout />}>
            <Route index element={<Dashboard />} />
          </Route>

      ) : (
        <Route path="*" element={<Navigate to="/login" replace />} />
      )}

      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};

export default App;