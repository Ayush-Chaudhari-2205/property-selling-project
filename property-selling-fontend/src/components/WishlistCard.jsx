// src/components/WishlistCard.jsx
import React from "react";
import { Link } from "react-router-dom";

const WishlistCard = ({ property }) => {
  return (
    <div className="card shadow-sm p-3">
      <div className="card-body">
        <h5 className="card-title">{property.propertyName}</h5>
        <p className="card-text"><strong>Type:</strong> {property.propertyType}</p>
        <p className="card-text"><strong>Price:</strong> ${property.price}</p>
        <p className="card-text"><strong>Location:</strong> {property.city}, {property.state}, {property.country}</p>
        <p className="card-text"><strong>Furnished:</strong> {property.furnished ? "Yes" : "No"}</p>
        <Link to={`/property/${property.propertyId}`} className="btn btn-primary mt-2">View Details</Link>
      </div>
    </div>
  );
};

export default WishlistCard;