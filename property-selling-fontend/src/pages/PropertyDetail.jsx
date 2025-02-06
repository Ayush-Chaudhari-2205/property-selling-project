// src/pages/PropertyDetail.jsx
import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { API } from "../API";

const PropertyDetail = () => {
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const response = await axios.get(`${API}/${id}`);
        
        if (response.data && response.data.data) {
          setProperty(response.data.data);
        } else {
          setProperty(null);
        }
      } catch (error) {
        console.error("Error fetching property:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProperty();
  }, [id]);

  if (loading) return <div className="text-center mt-4">Loading property details...</div>;
  if (!property) return <div className="text-center mt-4">Property not found</div>;

  return (
    <main className="page">
      <section className="clean-block clean-product dark">
        <div className="container">
          <div className="block-heading">
            <h2 className="text-info">{property.name}</h2>
            <p>{property.description}</p>
          </div>
          <div className="block-content">
            <div className="product-info">
              <div className="row">
                {/* Property Images */}
                <div className="col-md-6">
                  <div className="gallery">
                    <div id="product-preview" className="vanilla-zoom">
                      <div className="zoomed-image"></div>
                      <div className="sidebar">
                        {property.imageUrls.length > 0 ? (
                          property.imageUrls.map((img, index) => (
                            <img key={index} className="img-fluid d-block small-preview" src={img} alt="Property" />
                          ))
                        ) : (
                          <img className="img-fluid d-block small-preview" src="/assets/img/default-property.jpg" alt="Default Property" />
                        )}
                      </div>
                    </div>
                  </div>
                </div>

                {/* Property Info */}
                <div className="col-md-6">
                  <div className="info">
                    <h3>{property.propertyType} - {property.sellerName}</h3>
                    <h5 className="text-muted">{property.sellerEmail}</h5>

                    <div className="price">
                      <h3>${property.price}</h3>
                    </div>

                    <ul className="list-group">
                      <li className="list-group-item"><strong>Bedrooms:</strong> {property.bedrooms}</li>
                      <li className="list-group-item"><strong>Bathrooms:</strong> {property.bathrooms}</li>
                      <li className="list-group-item"><strong>Area:</strong> {property.area} sqft</li>
                      <li className="list-group-item"><strong>Furnished:</strong> {property.furnished ? "Yes" : "No"}</li>
                      <li className="list-group-item"><strong>Parking Available:</strong> {property.parkingAvailable ? "Yes" : "No"}</li>
                    </ul>

                    <button className="btn btn-primary mt-3">
                      <i className="icon-basket"></i> Contact Seller
                    </button>
                  </div>
                </div>
              </div>
            </div>

            {/* Description & Reviews Section */}
            <div className="product-info">
              <ul className="nav nav-tabs" role="tablist" id="myTab">
                <li className="nav-item" role="presentation">
                  <a className="nav-link active" role="tab" data-bs-toggle="tab" id="description-tab" href="#description">Description</a>
                </li>
                <li className="nav-item" role="presentation">
                  <a className="nav-link" role="tab" data-bs-toggle="tab" id="reviews-tab" href="#reviews">Reviews</a>
                </li>
              </ul>
              <div className="tab-content" id="myTabContent">
                {/* Description */}
                <div className="tab-pane fade show active description" role="tabpanel" id="description">
                  <p>{property.description}</p>
                </div>

                {/* Reviews */}
                <div className="tab-pane fade" role="tabpanel" id="reviews">
                  <div className="reviews">
                    {property.reviews?.length > 0 ? (
                      property.reviews.map((review, index) => (
                        <div key={index} className="review-item">
                          <h4>{review.comment}</h4>
                          <span className="text-muted">{review.buyer?.fullName || "Anonymous Buyer"}</span>
                        </div>
                      ))
                    ) : (
                      <p>No reviews yet.</p>
                    )}
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>
    </main>
  );
};

export default PropertyDetail;
