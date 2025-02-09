import React, { useContext, useEffect } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Profile from "./pages/Profile";
import Logout from "./pages/Logout";
import RootLayout from "./Layout/RootLayout";
import Home from "./pages/Home";
import NotFound from "./pages/NotFound";
import ContactUs from "./pages/ContactUs";
import DashboardLayout from "./Layout/DashboardLayout";
import Dashboard from "./pages/Dashboard";
import AuthContext from "./context/AuthContext";
import ListProperties from "./pages/seller/ListProperties";
import PropertyDetail from "./pages/PropertyDetail";
import AddProperty from "./pages/seller/AddProperty";
import PropertySearch from "./pages/PropertySearch";
import Wishlist from "./pages/Wishlist";
import ManageUsers from "./pages/admin/ManageUsers";
import ManageProperties from "./pages/admin/ManageProperties";
import ManageInquiries from "./pages/seller/ManageInquiries";
import UpdateProperty from "./pages/seller/UpdateProperty";
import ManagePropertyImages from "./pages/seller/ManagePropertyImages";

const App = () => {
  const { user, setUser } = useContext(AuthContext);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser));
      } catch (error) {
        console.error("Error parsing storedUser:", error);
        localStorage.removeItem("user");
      }
    }
  }, [setUser]);

  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<RootLayout />}>
        <Route index element={<Home />} />
        <Route path="/contact-us" element={<ContactUs />} />
        <Route path="/login" element={user ? <Navigate to="/" replace /> : <Login />} />
        <Route path="/signup" element={user ? <Navigate to="/" replace /> : <Signup />} />
        <Route path="/property/:id" element={<PropertyDetail />} /> 
        <Route path="/properties" element={<PropertySearch />} />
        <Route path="/logout" element={user ? <Navigate to="/" replace /> : <Logout />} />
        <Route path="/wishlist" element={<Wishlist />} />

      </Route>

      {/* Private Routes */}
      <Route element={<PrivateRoute />}>
        <Route path="/dashboard" element={<DashboardLayout />}>
          <Route index element={<Dashboard />} />
          <Route path="/dashboard/admin/users" element={<ManageUsers />} />
          <Route path="/dashboard/admin/properties" element={<ManageProperties />} />
        </Route>
        <Route path="/profile" element={<Profile />} />
      </Route>

       

      {/* Seller Routes - Accessible Only to Sellers */}
      <Route element={<PrivateRoute requiredRole="SELLER" />}>
        <Route path="/seller/properties" element={<ListProperties />} />
        <Route path="/seller/property/:id" element={<PropertyDetail />} />
        <Route path="/seller/property/add" element={<AddProperty />} /> 
        <Route path="/seller/inquiries" element={<ManageInquiries />} />
        <Route path="property/update/:id" element={<UpdateProperty />} />
        <Route path="property/images/:id" element={<ManagePropertyImages />} />
      </Route>

      {/* Catch-All Route for Not Found */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default App;
