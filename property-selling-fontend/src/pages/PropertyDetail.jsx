import React, { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { API } from "../API";
import AuthContext from "../context/AuthContext";

const PropertyDetail = () => {
  const { user } = useContext(AuthContext);
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");
  const [review, setReview] = useState("");
  const [rating, setRating] = useState("");
  const [reviews, setReviews] = useState([]);
  const [showReviewForm, setShowReviewForm] = useState(false);


  // ✅ Move fetchReviews here, so it's accessible globally
  const fetchReviews = async () => {
    try {
      const response = await axios.get(`${API}/review/property/${id}`, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });
      setReviews(response.data?.data || []);
    } catch (error) {
      console.error("Error fetching reviews:", error);
    }
  };

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const response = await axios.get(`${API}/property/${id}`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        setProperty(response.data?.data || null);
      } catch (error) {
        console.error("Error fetching property:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProperty();
    fetchReviews();
  }, [id]);

  const addToWishlist = async () => {
    try {
      await axios.post(
        `${API}/wishlist/add`,
        { buyerId: user?.user_id, propertyId: id },
        { headers: { Authorization: `Bearer ${user.jwt}` } }
      );
      alert("Property added to wishlist!");
    } catch (error) {
      console.error("Failed to add to wishlist:", error);
      alert("Error adding property to wishlist");
    }
  };

  const handleContactSubmit = async (e) => {
    e.preventDefault();
    if (!user || !user.user_id) {
      alert("You need to be logged in to contact the seller.");
      return;
    }

    try {
      await axios.post(
        `${API}/inquiry/submit`,
        { propertyId: id, buyerId: user.user_id, message },
        { headers: { Authorization: `Bearer ${user.jwt}` } }
      );
      alert("Message sent to seller!");
      setMessage("");
    } catch (error) {
      console.error("Error sending message:", error);
      alert("Failed to send message to seller");
    }
  };

  const handleReviewSubmit = async (e) => {
    e.preventDefault();
    if (!user || !user.user_id) {
      alert("You need to be logged in to add a review.");
      return;
    }
    if (!rating || rating < 1 || rating > 5) {
      alert("Please enter a valid rating between 1 and 5.");
      return;
    }

    try {
      await axios.post(
        `${API}/review/add`,
        { propertyId: id, buyerId: user.user_id, rating: parseInt(rating), comment: review },
        { headers: { Authorization: `Bearer ${user.jwt}` } }
      );
      
      
      alert("Review added successfully!");
      setReview("");
      setRating("");
      fetchReviews(); // ✅ Fetch new reviews immediately
    } catch (error) {
      console.error("Error adding review:", error);
      alert("Failed to add review");
    }
  };

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
                <div className="col-md-6">
                  <div className="gallery">
                    {property.imageUrls?.length > 0 ? (
                      property.imageUrls.map((img, index) => {
                                    const imageSrc = `data:image/jpeg;base64,${img}`;
                        return  <img key={index} className="img-fluid d-block small-preview" src={imageSrc} alt="Property" />
                      }

                      )
                    ) : (
                      <img className="img-fluid d-block small-preview" src="/assets/img/default-property.jpg" alt="Default Property" />
                    )}
                  </div>
                </div>
                <div className="col-md-6">
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
                  <button className="btn btn-outline-danger mt-3" onClick={addToWishlist}>
                    <i className="icon-heart"></i> Add to Wishlist
                  </button>
                </div>
              </div>
            </div>

            <div className="product-info">
              <h4>Reviews</h4>
              {reviews.length > 0 ? (
                reviews.map((review, index) => (
                  <div key={index} className="review-item border p-2 mt-2">
                    <h5>{review.comment}</h5>
                    <p className="text-muted">By {review.buyerName} - Rating: {review.rating}/5</p>
                  </div>
                ))
              ) : (
                <p>No reviews yet.</p>
              )}
              <button className="btn btn-outline-primary mt-3" onClick={() => setShowReviewForm(!showReviewForm)}>
                {showReviewForm ? "Cancel" : "Add Review"}
              </button>
              {showReviewForm && (
                <form onSubmit={handleReviewSubmit} className="mt-3">
                  <div className="mb-3">
                    <input type="number" className="form-control" min="1" max="5" placeholder="Rating (1-5)" value={rating} onChange={(e) => setRating(e.target.value)} required />
                  </div>
                  <div className="mb-3">
                    <textarea className="form-control" rows="3" placeholder="Write your review..." value={review} onChange={(e) => setReview(e.target.value)} required></textarea>
                  </div>
                  <button type="submit" className="btn btn-success">Submit Review</button>
                </form>
              )}
            </div>

            <div className="contact-form mt-4">
              <h4>Contact Seller</h4>
              <form onSubmit={handleContactSubmit}>
                <div className="mb-3">
                  <textarea className="form-control" rows="4" value={message} onChange={(e) => setMessage(e.target.value)} placeholder="Type your message here..." required></textarea>
                </div>
                <button type="submit" className="btn btn-primary">Send Message</button>
              </form>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
};

export default PropertyDetail;
