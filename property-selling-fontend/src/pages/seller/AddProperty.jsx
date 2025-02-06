// src/pages/seller/AddProperty.jsx
import React, { useState, useContext } from "react";
import axios from "axios";
import AuthContext from "../../context/AuthContext";
import { API } from "../../API";

const AddProperty = () => {
  const { user } = useContext(AuthContext);
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    price: "",
    bedrooms: "",
    bathrooms: "",
    area: "",
    furnished: false,
    parkingAvailable: false,
    propertyType: "HOUSE",
    addressLine: "",
    city: "",
    state: "",
    country: "",
    pinCode: "",
    sellerId: user ? user.user_id : "", // Auto-select current seller ID
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      await axios.post(`${API}/property/add`, formData, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });
      alert("Property added successfully!");
    } catch (error) {
      console.error("Error adding property:", error);
    }
  };

  return (
    <div className="profile-container card shadow p-4"> {/* Using Profile Page styles */}
      <h2 className="text-primary text-center">Add Property</h2>
      <form onSubmit={handleSubmit} className="profile-form">

        {/* Property Name */}
        <div className="form-group">
          <label>Property Name:</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} required className="form-control" />
        </div>

        {/* Description */}
        <div className="form-group">
          <label>Description:</label>
          <textarea name="description" value={formData.description} onChange={handleChange} required className="form-control" />
        </div>

        {/* Price */}
        <div className="form-group">
          <label>Price:</label>
          <input type="number" name="price" value={formData.price} onChange={handleChange} required className="form-control" />
        </div>

        {/* Bedrooms & Bathrooms */}
        <div className="row">
          <div className="col-md-6 form-group">
            <label>Bedrooms:</label>
            <input type="number" name="bedrooms" value={formData.bedrooms} onChange={handleChange} required className="form-control" />
          </div>
          <div className="col-md-6 form-group">
            <label>Bathrooms:</label>
            <input type="number" name="bathrooms" value={formData.bathrooms} onChange={handleChange} required className="form-control" />
          </div>
        </div>

        {/* Area */}
        <div className="form-group">
          <label>Area (sq ft):</label>
          <input type="number" name="area" value={formData.area} onChange={handleChange} required className="form-control" />
        </div>

        {/* Furnished & Parking */}
        <div className="form-group form-check">
          <input type="checkbox" name="furnished" checked={formData.furnished} onChange={handleChange} className="form-check-input" />
          <label className="form-check-label">Furnished</label>
        </div>
        <div className="form-group form-check">
          <input type="checkbox" name="parkingAvailable" checked={formData.parkingAvailable} onChange={handleChange} className="form-check-input" />
          <label className="form-check-label">Parking Available</label>
        </div>

        {/* Property Type Dropdown */}
        <div className="form-group">
          <label>Property Type:</label>
          <select name="propertyType" value={formData.propertyType} onChange={handleChange} className="form-control">
            <option value="HOUSE">House</option>
            <option value="APARTMENT">Apartment</option>
            <option value="VILLA">Villa</option>
            <option value="COMMERCIAL">Commercial</option>
            <option value="LAND">Land</option>
          </select>
        </div>

        {/* Address Section */}
        <h3 className="mt-3">Address</h3>

        <div className="form-group">
          <label>Street:</label>
          <input type="text" name="addressLine" value={formData.addressLine} onChange={handleChange} required className="form-control" />
        </div>

        <div className="row">
          <div className="col-md-6 form-group">
            <label>City:</label>
            <input type="text" name="city" value={formData.city} onChange={handleChange} required className="form-control" />
          </div>
          <div className="col-md-6 form-group">
            <label>State:</label>
            <input type="text" name="state" value={formData.state} onChange={handleChange} required className="form-control" />
          </div>
        </div>

        <div className="row">
          <div className="col-md-6 form-group">
            <label>Country:</label>
            <input type="text" name="country" value={formData.country} onChange={handleChange} required className="form-control" />
          </div>
          <div className="col-md-6 form-group">
            <label>Pin Code:</label>
            <input type="text" name="pinCode" value={formData.pinCode} onChange={handleChange} required className="form-control" />
          </div>
        </div>

        {/* Submit Button */}
        <div className="text-center mt-3">
          <button type="submit" className="btn btn-success">Add Property</button>
        </div>

      </form>
    </div>
  );
};

export default AddProperty;
