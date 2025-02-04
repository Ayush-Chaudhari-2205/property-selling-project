// src/pages/Profile.jsx
import React, { useState, useEffect, useContext } from "react";
import AuthContext from "../context/AuthContext";
import axios from "axios";

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
    const fetchProfile = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get("/user/profile", {
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
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      await axios.put("/user/update", formData, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert("Profile updated successfully!");
      setIsEditing(false);
      setProfile((prev) => ({ ...prev, ...formData }));
    } catch (error) {
      console.error("Error updating profile:", error);
    }
  };

  if (loading) return <div>Loading...</div>;
  if (!profile) return <div>Profile not found</div>;

  return (
    <div className="profile-container">
      <h2>Profile</h2>
      {isEditing ? (
        <form onSubmit={handleSubmit}>
          <label>Full Name:</label>
          <input type="text" name="fullName" value={formData.fullName} onChange={handleChange} required />
          <label>Mobile Number:</label>
          <input type="text" name="mobileNumber" value={formData.mobileNumber} onChange={handleChange} required />
          <label>Aadhaar Card Number:</label>
          <input type="text" name="aadhaarCard" value={formData.aadhaarCard} onChange={handleChange} />
          <h3>Address</h3>
          <label>Street:</label>
          <input type="text" name="addressLine" value={formData.addressLine} onChange={handleChange} />
          <label>City:</label>
          <input type="text" name="city" value={formData.city} onChange={handleChange} />
          <label>State:</label>
          <input type="text" name="state" value={formData.state} onChange={handleChange} />
          <label>Country:</label>
          <input type="text" name="country" value={formData.country} onChange={handleChange} />
          <label>Pin Code:</label>
          <input type="text" name="pinCode" value={formData.pinCode} onChange={handleChange} />
          <button type="submit">Save</button>
        </form>
      ) : (
        <>
          <p><strong>Full Name:</strong> {profile.fullName}</p>
          <p><strong>Email:</strong> {profile.email}</p>
          <p><strong>Mobile Number:</strong> {profile.mobileNumber}</p>
          <p><strong>User Type:</strong> {profile.userType}</p>
          <p><strong>Aadhaar Card Number:</strong> {profile.aadhaarCard?.cardNo || "Not provided"}</p>
          <h3>Address</h3>
          <p><strong>Street:</strong> {profile.address?.addressLine || "Not provided"}</p>
          <p><strong>City:</strong> {profile.address?.city || "Not provided"}</p>
          <p><strong>State:</strong> {profile.address?.state || "Not provided"}</p>
          <p><strong>Country:</strong> {profile.address?.country || "Not provided"}</p>
          <p><strong>Pin Code:</strong> {profile.address?.pinCode || "Not provided"}</p>
          <button onClick={() => setIsEditing(true)}>Edit Profile</button>
        </>
      )}
    </div>
  );
};

export default Profile;
