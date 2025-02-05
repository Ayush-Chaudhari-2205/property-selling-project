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
  const { user,setUser } = useContext(AuthContext);

  const isAuthenticated =  () => {

    // console.log("Stored Token After Setting:", localStorage.getItem("token")); // Verify storage


          const localuser =  localStorage.getItem("user")
          console.log("user in app",user);
          
          if(localuser!== null)
          {
            console.log("working in user in app");
            
      setUser(JSON.parse(localuser));
          }

    }


  

  useEffect(() => {
    return isAuthenticated

  }, [])
  

  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<RootLayout />}>
        <Route index element={<Home />} />
        <Route path="/contact-us" element={<ContactUs />} />
        <Route path="/login" element={user ? <Navigate to="/" replace /> : <Login />} />
        <Route path="/signup" element={user ? <Navigate to="/" replace /> : <Signup />} />
      </Route>


          <Route path="/dashboard" element={<DashboardLayout />}>
            <Route index element={<Dashboard />} />
          </Route>
          <Route path="/profile" element={<Profile />} /> {/* Add this Route */}


      {/* Redirect all other unknown routes */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default App;
