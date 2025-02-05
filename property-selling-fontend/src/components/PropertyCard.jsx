// src/components/PropertyCard.jsx
import React from "react";
import { Link } from "react-router-dom";

const PropertyCard = ({ property }) => {
  return (
    <div className="card property-card shadow-sm">
      {/* Property Image */}
      <img
        src={property?.images?.length > 0 ? property.images[0].imageUrl : "/assets/img/default-property.jpg"}
        className="card-img-top property-img"
        alt={property.name}
      />

      {/* Card Body */}
      <div className="card-body">
        <h5 className="card-title">{property.name}</h5>
        <p className="card-text text-muted">{property.address?.city}, {property.address?.state}</p>
        <h6 className="text-primary">${property.price}</h6>

        {/* Property Features */}
        <div className="property-features">
          <span>{property.bedrooms} Beds</span> | <span>{property.bathrooms} Baths</span> | <span>{property.area} sqft</span>
        </div>

        {/* Buttons */}
        <div className="d-flex justify-content-between mt-3">
          <Link to={`/properties/${property.id}`} className="btn btn-sm btn-outline-primary">
            View Details
          </Link>
          <button className="btn btn-sm btn-success">Contact Seller</button>
        </div>
      </div>
    </div>
  );
};

export default PropertyCard;
