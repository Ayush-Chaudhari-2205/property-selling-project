// src/pages/Profile.jsx
import React, { useState, useEffect, useContext } from "react";
import AuthContext from "../context/AuthContext";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import "../assets/css/profile.css";

const Profile = () => {
  const { user } = useContext(AuthContext);
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    fullName: "",
    mobileNumber: "",
    aadhaarCard: "",
    addressLine: "",
    city: "",
    state: "",
    country: "",
    pinCode: "",
  });
  const [isEditing, setIsEditing] = useState(false);

  const token = localStorage.getItem("token");
  const decodedUser = token ? jwtDecode(token) : null;
  const userEmail = decodedUser?.email; // Extract user email from token

  useEffect(() => {
    if (!userEmail) {
      console.error("User email not found in JWT");
      return;
    }

    const fetchProfile = async () => {
      try {
        const response = await axios.get(`/user/profile/get-by-email?email=${userEmail}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProfile(response.data);
        setFormData({
          fullName: response.data.fullName,
          mobileNumber: response.data.mobileNumber,
          aadhaarCard: response.data.aadhaarCard?.cardNo || "",
          addressLine: response.data.address?.addressLine || "",
          city: response.data.address?.city || "",
          state: response.data.address?.state || "",
          country: response.data.address?.country || "",
          pinCode: response.data.address?.pinCode || "",
        });
      } catch (error) {
        console.error("Error fetching profile:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProfile();
  }, [userEmail]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`/user/update/update-by-email?email=${userEmail}`, formData, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert("Profile updated successfully!");
      setIsEditing(false);
      setProfile((prev) => ({ ...prev, ...formData }));
    } catch (error) {
      console.error("Error updating profile:", error);
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (!profile) return <div className="error">Profile not found</div>;

  return (
    <div className="profile-container card shadow p-4">
      <h2 className="text-primary text-center">Profile</h2>
      {isEditing ? (
        <form onSubmit={handleSubmit} className="profile-form">
          <div className="form-group">
            <label>Full Name:</label>
            <input type="text" name="fullName" value={formData.fullName} onChange={handleChange} required className="form-control" />
          </div>
          <div className="form-group">
            <label>Mobile Number:</label>
            <input type="text" name="mobileNumber" value={formData.mobileNumber} onChange={handleChange} required className="form-control" />
          </div>
          <div className="form-group">
            <label>Aadhaar Card Number:</label>
            <input type="text" name="aadhaarCard" value={formData.aadhaarCard} onChange={handleChange} className="form-control" />
          </div>
          <h3 className="mt-3">Address</h3>
          <div className="form-group">
            <label>Street:</label>
            <input type="text" name="addressLine" value={formData.addressLine} onChange={handleChange} className="form-control" />
          </div>
          <div className="form-group">
            <label>City:</label>
            <input type="text" name="city" value={formData.city} onChange={handleChange} className="form-control" />
          </div>
          <div className="form-group">
            <label>State:</label>
            <input type="text" name="state" value={formData.state} onChange={handleChange} className="form-control" />
          </div>
          <div className="form-group">
            <label>Country:</label>
            <input type="text" name="country" value={formData.country} onChange={handleChange} className="form-control" />
          </div>
          <div className="form-group">
            <label>Pin Code:</label>
            <input type="text" name="pinCode" value={formData.pinCode} onChange={handleChange} className="form-control" />
          </div>
          <button type="submit" className="btn btn-success mt-3">Save</button>
        </form>
      ) : (
        <button onClick={() => setIsEditing(true)} className="btn btn-primary mt-3">Edit Profile</button>
      )}
    </div>
  );
};

export default Profile;
