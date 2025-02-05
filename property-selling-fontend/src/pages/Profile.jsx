import React, { useState, useEffect, useContext } from "react";
import AuthContext from "../context/AuthContext";
import axios from "axios";
import "../assets/css/profile.css";
import { API } from "../API";

const Profile = () => {
  const { user } = useContext(AuthContext);
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);

  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    mobileNumber: "",
    userType: "",
    question: "",
    answer: "",
    aadhaarNumber: "",
    address: {
      addressLine: "",
      city: "",
      state: "",
      country: "",
      pinCode: "",
    },
  });

  useEffect(() => {
    if (user?.email) {
      fetchProfile();
    }
  }, [user]);

  const fetchProfile = async () => {
    if (!user?.email) return;

    try {
      console.log("Fetching profile for:", user.email);
      const response = await axios.get(`${API}/user/profile/get-by-email?email=${user.email}`, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });

      if (!response.data || !response.data.data) {
        throw new Error("Invalid response from server");
      }

      const profileData = response.data.data;
      console.log("Profile Data:", profileData);

      setProfile(profileData);

      setFormData({
        fullName: profileData.fullName || "",
        email: profileData.email || "",
        mobileNumber: profileData.mobileNumber || "",
        userType: profileData.userType || "",
        question: profileData.question || "",
        answer: "", // Keep answer empty initially for security
        aadhaarNumber: profileData.aadhaarNumber || "",
        address: {
          addressLine: profileData.address?.addressLine || "",
          city: profileData.address?.city || "",
          state: profileData.address?.state || "",
          country: profileData.address?.country || "",
          pinCode: profileData.address?.pinCode || "",
        },
      });
    } catch (error) {
      console.error("Error fetching profile:", error);
      alert("Failed to load profile. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (["addressLine", "city", "state", "country", "pinCode"].includes(name)) {
      // Update nested address fields
      setFormData((prev) => ({
        ...prev,
        address: { ...prev.address, [name]: value },
      }));
    } else {
      // Update other fields
      setFormData((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log("Submitting updated profile:", formData);

      await axios.put(`${API}/user/update/update-by-email?email=${user.email}`, formData, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });

      alert("Profile updated successfully!");
      setIsEditing(false);
      fetchProfile(); // ✅ Fetch updated profile
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
            <label>Email:</label>
            <input type="email" name="email" value={formData.email} onChange={handleChange} required className="form-control" disabled />
          </div>

          <div className="form-group">
            <label>Mobile Number:</label>
            <input type="text" name="mobileNumber" value={formData.mobileNumber} onChange={handleChange} required className="form-control" />
          </div>

          <div className="form-group">
            <label>User Type:</label>
            <input type="text" name="userType" value={formData.userType} className="form-control" disabled />
          </div>

          <div className="form-group">
            <label>Security Question:</label>
            <input type="text" name="question" value={formData.question} className="form-control" disabled />
          </div>

          <div className="form-group">
            <label>Security Answer:</label>
            <input type="text" name="answer" value={formData.answer} onChange={handleChange} required className="form-control" />
          </div>

          <div className="form-group">
            <label>Aadhaar Card Number:</label>
            <input type="text" name="aadhaarNumber" value={formData.aadhaarNumber} onChange={handleChange} className="form-control" />
          </div>

          <h3 className="mt-3">Address</h3>
          <div className="form-group">
            <label>Street:</label>
            <input type="text" name="addressLine" value={formData.address.addressLine} onChange={handleChange} className="form-control" />
          </div>

          <div className="form-group">
            <label>City:</label>
            <input type="text" name="city" value={formData.address.city} onChange={handleChange} className="form-control" />
          </div>

          <div className="form-group">
            <label>State:</label>
            <input type="text" name="state" value={formData.address.state} onChange={handleChange} className="form-control" />
          </div>

          <div className="form-group">
            <label>Country:</label>
            <input type="text" name="country" value={formData.address.country} onChange={handleChange} className="form-control" />
          </div>

          <div className="form-group">
            <label>Pin Code:</label>
            <input type="text" name="pinCode" value={formData.address.pinCode} onChange={handleChange} className="form-control" />
          </div>

          <button type="submit" className="btn btn-success mt-3">Save</button>
          <button type="button" className="btn btn-secondary mt-3 ms-2" onClick={() => setIsEditing(false)}>Cancel</button>
        </form>
      ) : (
        <>
          <p><strong>Full Name:</strong> {profile.fullName}</p>
          <p><strong>Email:</strong> {profile.email}</p>
          <p><strong>Mobile Number:</strong> {profile.mobileNumber}</p>
          <p><strong>User Type:</strong> {profile.userType}</p>
          <p><strong>Security Question:</strong> {profile.question}</p>
          <p><strong>Aadhaar Number:</strong> {profile.aadhaarNumber}</p>

          <h3 className="mt-3">Address</h3>
          <p><strong>Street:</strong> {profile.address?.addressLine}</p>
          <p><strong>City:</strong> {profile.address?.city}</p>
          <p><strong>State:</strong> {profile.address?.state}</p>
          <p><strong>Country:</strong> {profile.address?.country}</p>
          <p><strong>Pin Code:</strong> {profile.address?.pinCode}</p>

          <button onClick={() => setIsEditing(true)} className="btn btn-primary mt-3">Edit Profile</button>
        </>
      )}
    </div>
  );
};

export default Profile;
