// src/pages/seller/ListProperties.jsx
import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import AuthContext from "../../context/AuthContext";

const ListProperties = () => {
  const { user } = useContext(AuthContext);
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get("/seller/properties", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProperties(response.data);
      } catch (error) {
        console.error("Error fetching properties:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProperties();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (properties.length === 0) return <div>No properties found</div>;

  return (
    <div className="list-properties-container">
      <h2>Your Properties</h2>
      <ul>
        {properties.map((property) => (
          <li key={property.id}>
            <h3>{property.name}</h3>
            <p>{property.description}</p>
            <p><strong>Price:</strong> ${property.price}</p>
            <p><strong>Bedrooms:</strong> {property.bedrooms}</p>
            <p><strong>Bathrooms:</strong> {property.bathrooms}</p>
            <p><strong>Area:</strong> {property.area} sq ft</p>
            <p><strong>Furnished:</strong> {property.furnished ? "Yes" : "No"}</p>
            <p><strong>Parking Available:</strong> {property.parkingAvailable ? "Yes" : "No"}</p>
            <p><strong>Property Type:</strong> {property.propertyType}</p>
            <p><strong>Location:</strong> {property.address.city}, {property.address.state}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ListProperties;
