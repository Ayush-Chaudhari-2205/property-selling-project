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
        const response = await axios.get(`${API}/properties/${id}`);
        setProperty(response.data);
      } catch (error) {
        console.error("Error fetching property:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProperty();
  }, [id]);

  if (loading) return <div>Loading...</div>;
  if (!property) return <div>Property not found</div>;

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
                <div className="col-md-6">
                  <div className="gallery">
                    <div id="product-preview" className="vanilla-zoom">
                      <div className="zoomed-image"></div>
                      <div className="sidebar">
                        {property.images.map((img, index) => (
                          <img key={index} className="img-fluid d-block small-preview" src={img.imageUrl} alt="Property" />
                        ))}
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-md-6">
                  <div className="info">
                    <h3>{property.address.city}, {property.address.state}</h3>
                    <div className="price">
                      <h3>${property.price}</h3>
                    </div>
                    <button className="btn btn-primary" type="button">
                      <i className="icon-basket"></i> Contact Seller
                    </button>
                    <div className="summary">
                      <p>{property.summary}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="product-info">
              <div>
                <ul className="nav nav-tabs" role="tablist" id="myTab">
                  <li className="nav-item" role="presentation">
                    <a className="nav-link active" role="tab" data-bs-toggle="tab" id="description-tab" href="#description">Description</a>
                  </li>
                  <li className="nav-item" role="presentation">
                    <a className="nav-link" role="tab" data-bs-toggle="tab" id="reviews-tab" href="#reviews">Reviews</a>
                  </li>
                </ul>
                <div className="tab-content" id="myTabContent">
                  <div className="tab-pane fade show active description" role="tabpanel" id="description">
                    <p>{property.description}</p>
                  </div>
                  <div className="tab-pane fade" role="tabpanel" id="reviews">
                    <div className="reviews">
                      {property.reviews.map((review, index) => (
                        <div key={index} className="review-item">
                          <h4>{review.comment}</h4>
                          <span className="text-muted">{review.buyer.fullName}</span>
                        </div>
                      ))}
                    </div>
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
