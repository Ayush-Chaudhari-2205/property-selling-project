import React, { useContext } from "react";
import { Link } from "react-router-dom";
import "../assets/css/PropertyCard.css"; // Ensure you have styling for the card
import AuthContext from "../context/AuthContext";

const PropertyCard = ({ property }) => {

console.log(property);

   const { user } = useContext(AuthContext);

// const blob = new Blob([byteArray], { type: "image/png" });
//     console.log(blob)
//     const imageUrl = URL.createObjectURL(blob);
            const imageSrc = `data:image/jpeg;base64,${property?.imageUrls[0]}`;

  return (
    <div className="card property-card shadow-sm">
      {/* Property Image */}

      <img
        src={
          property?.imageUrls?.length > 0
            ? imageSrc
            : "/assets/img/default-property.jpg"
        }
        className="card-img-top property-img"
        alt={property.name}
      />

      {/* Card Body */}
      <div className="card-body">
        <h5 className="card-title">{property.name}</h5>
        <p className="card-text text-muted">{property.propertyType}</p>
        <h6 className="text-primary">${property.price}</h6>

        {/* Property Features */}
        <div className="property-features">
          <span>{property.bedrooms} Beds</span> |{" "}
          <span>{property.bathrooms} Baths</span> |{" "}
          <span>{property.area} sqft</span>
        </div>

        {/* View Details Button */}
        <div className="d-flex justify-content-center mt-3">
          <Link
            to={`/property/${property.id}`}
            className="btn btn-sm btn-outline-primary"
          >
            View Details
          </Link>
          {user && user.role === "SELLER" && (
            <>
              <Link to={`/property/update/${property.id}`} className="btn btn-warning btn-sm">
            Update
          </Link>
          <Link to={`/property/images/${property.id}`} className="btn btn-secondary btn-sm">
              Images
            </Link>
            </>
          
          
        )}
        </div>
      </div>
    </div>
  );
};

export default PropertyCard;
