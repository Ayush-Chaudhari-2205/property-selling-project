import React, { useContext, useEffect } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Profile from "./pages/Profile"; // Ensure Profile is imported
import Logout from "./pages/Logout";
import RootLayout from "./Layout/RootLayout";
import Home from "./pages/Home";
import NotFound from "./pages/NotFound";
import ContactUs from "./pages/ContactUs";
import DashboardLayout from "./Layout/DashboardLayout";
import Dashboard from "./pages/Dashboard";
import AuthContext from "./context/AuthContext";

const App = () => {
  const { user, setUser } = useContext(AuthContext);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    console.log("storedUser App", storedUser);

    if (storedUser) {
      // Ensure it's not null or undefined
      console.log("working");
      try {
        setUser(JSON.parse(storedUser));
      } catch (error) {
        console.error("Error parsing storedUser:", error);
        localStorage.removeItem("user"); // Remove corrupted data if any
      }
    }
  }, []);

  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<RootLayout />}>
        <Route index element={<Home />} />
        <Route path="/contact-us" element={<ContactUs />} />
        <Route
          path="/login"
          element={ <Login />}
        />
        <Route
          path="/signup"
          element={ <Signup />}
        />
      </Route>

      {/* Private Routes */}
      <Route path="/dashboard" element={<PrivateRoute />}>
        <Route element={<DashboardLayout />}>
          <Route index element={<Dashboard />} />
        </Route>
      </Route>

      <Route path="/profile" element={<PrivateRoute />}>
        <Route index element={<Profile />} />
      </Route>

      {/* Redirect all unknown routes */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default App;
