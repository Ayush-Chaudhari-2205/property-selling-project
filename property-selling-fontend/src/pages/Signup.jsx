// src/pages/Signup.jsx
import React, { useState, useContext } from "react";
import AuthContext from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { API } from "../API";

const Signup = () => {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    userType: "BUYER", // Default role
    fullName: "",
    email: "",
    mobileNumber: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {4
      const response = await axios.post(`${API}/user/signup`, formData);
      alert(response.data.message);
      login(response.data.data.token);
      navigate("/dashboard");
    } catch (error) {
      alert("Signup failed: " + (error.response?.data?.message || error.message));
    }
  };

  return (
    <main className="page">
      <section className="clean-block clean-form dark">
        <div className="container">
          <div className="block-heading">
            <h2 className="text-info">Registration</h2>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna, dignissim nec auctor in, mattis vitae leo.
            </p>
          </div>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label" htmlFor="fullName">Name</label>
              <input className="form-control item" type="text" id="fullName" name="fullName" value={formData.fullName} onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <label className="form-label" htmlFor="password">Password</label>
              <input className="form-control item" type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <label className="form-label" htmlFor="email">Email</label>
              <input className="form-control item" type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <label className="form-label" htmlFor="mobileNumber">Mobile Number</label>
              <input className="form-control item" type="text" id="mobileNumber" name="mobileNumber" value={formData.mobileNumber} onChange={handleChange} required />
            </div>
            <button className="btn btn-primary" type="submit">Sign Up</button>
          </form>
        </div>
      </section>
    </main>
  );
};

export default Signup;
