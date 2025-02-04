// src/pages/seller/AddProperty.jsx
import React, { useState, useContext } from "react";
import axios from "axios";
import AuthContext from "../../context/AuthContext";

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
    sellerId: user ? user.id : "", // Auto-select current seller ID
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
      await axios.post("/seller/add-property", formData, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert("Property added successfully!");
    } catch (error) {
      console.error("Error adding property:", error);
    }
  };

  return (
    <div className="add-property-container">
      <h2>Add Property</h2>
      <form onSubmit={handleSubmit}>
        <label>Property Name:</label>
        <input type="text" name="name" value={formData.name} onChange={handleChange} required />
        
        <label>Description:</label>
        <textarea name="description" value={formData.description} onChange={handleChange} required />
        
        <label>Price:</label>
        <input type="number" name="price" value={formData.price} onChange={handleChange} required />
        
        <label>Bedrooms:</label>
        <input type="number" name="bedrooms" value={formData.bedrooms} onChange={handleChange} required />
        
        <label>Bathrooms:</label>
        <input type="number" name="bathrooms" value={formData.bathrooms} onChange={handleChange} required />
        
        <label>Area (sq ft):</label>
        <input type="number" name="area" value={formData.area} onChange={handleChange} required />
        
        <label>
          <input type="checkbox" name="furnished" checked={formData.furnished} onChange={handleChange} /> Furnished
        </label>
        
        <label>
          <input type="checkbox" name="parkingAvailable" checked={formData.parkingAvailable} onChange={handleChange} /> Parking Available
        </label>
        
        <label>Property Type:</label>
        <select name="propertyType" value={formData.propertyType} onChange={handleChange}>
          <option value="HOUSE">House</option>
          <option value="APARTMENT">Apartment</option>
          <option value="VILLA">Villa</option>
          <option value="COMMERCIAL">Commercial</option>
          <option value="LAND">Land</option>
        </select>
        
        <h3>Address</h3>
        <label>Street:</label>
        <input type="text" name="addressLine" value={formData.addressLine} onChange={handleChange} required />
        
        <label>City:</label>
        <input type="text" name="city" value={formData.city} onChange={handleChange} required />
        
        <label>State:</label>
        <input type="text" name="state" value={formData.state} onChange={handleChange} required />
        
        <label>Country:</label>
        <input type="text" name="country" value={formData.country} onChange={handleChange} required />
        
        <label>Pin Code:</label>
        <input type="text" name="pinCode" value={formData.pinCode} onChange={handleChange} required />
        
        <button type="submit">Add Property</button>
      </form>
    </div>
  );
};

export default AddProperty;
