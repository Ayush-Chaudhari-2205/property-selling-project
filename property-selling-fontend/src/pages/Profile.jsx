import React, { useState, useEffect, useContext } from "react";
import AuthContext from "../context/AuthContext";
import axios from "axios";
import "../assets/css/profile.css";
import { API } from "../API";

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

  useEffect(() => {
    if (user?.email) {
      fetchProfile();
    }
  }, [user]); // ✅ Re-fetch profile when user updates

  const fetchProfile = async () => {
    if (!user?.email) return;
    try {
      console.log("Fetching profile for:", user.email);
      const response = await axios.get(`${API}/user/profile/get-by-email?email=${user.email}`, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });

      if (!response.data) throw new Error("Invalid response from server");

      console.log("Profile Data:", response.data);
      setProfile(response.data);
      setFormData({
        fullName: response.data.fullName || "",
        mobileNumber: response.data.mobileNumber || "",
        aadhaarCard: response.data.aadhaarCard?.cardNo || "",
        addressLine: response.data.address?.addressLine || "",
        city: response.data.address?.city || "",
        state: response.data.address?.state || "",
        country: response.data.address?.country || "",
        pinCode: response.data.address?.pinCode || "",
      });
    } catch (error) {
      console.error("Error fetching profile:", error);
      alert("Failed to load profile. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log("Updating profile for:", user.email, formData);
      await axios.put(`${API}/update/update-by-email?email=${user.email}`, formData, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });

      alert("Profile updated successfully!");
      setIsEditing(false);
      setProfile((prev) => ({ ...prev, ...formData }));
    } catch (error) {
      console.error("Error updating profile:", error);
      alert("Failed to update profile.");
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
          <button type="submit" className="btn btn-success mt-3">Save</button>
          <button type="button" className="btn btn-secondary mt-3 ms-2" 
            onClick={() => { 
              setFormData({
                fullName: profile.fullName || "",
                mobileNumber: profile.mobileNumber || "",
                aadhaarCard: profile.aadhaarCard?.cardNo || "",
                addressLine: profile.address?.addressLine || "",
                city: profile.address?.city || "",
                state: profile.address?.state || "",
                country: profile.address?.country || "",
                pinCode: profile.address?.pinCode || "",
              });
              setIsEditing(false);
            }}
          >
            Cancel
          </button>
        </form>
      ) : (
        <>
          <p><strong>Full Name:</strong> {profile.fullName}</p>
          <p><strong>Email:</strong> {user.email}</p>
          <button onClick={() => setIsEditing(true)} className="btn btn-primary mt-3">Edit Profile</button>
        </>
      )}
    </div>
  );
};

export default Profile;
