// src/pages/Login.jsx
import React, { useState, useContext } from "react";
import AuthContext from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { API } from "../API";


const Login = () => {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(`${API}/user/signin`, formData);
      const token = response.data.jwt;
      localStorage.setItem("token", token);
      alert("Login successful");
      login(token);
      navigate("/dashboard");
    } catch (error) {
        console.error(error);
        
      alert("Login failed: " + (error.response?.data?.message || error.message));
    }
  };

  return (
    <main className="page">
      <section className="clean-block clean-form dark">
        <div className="container">
          <div className="block-heading">
            <h2 className="text-info">Log In</h2>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna, dignissim nec auctor in, mattis vitae leo.
            </p>
          </div>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label" htmlFor="email">Email</label>
              <input className="form-control item" type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <label className="form-label" htmlFor="password">Password</label>
              <input className="form-control" type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <div className="form-check">
                <input className="form-check-input" type="checkbox" id="checkbox" />
                <label className="form-check-label" htmlFor="checkbox">Remember me</label>
              </div>
            </div>
            <button className="btn btn-primary" type="submit">Log In</button>
          </form>
        </div>
      </section>
    </main>
  );
};

export default Login;
