import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import AuthContext from "../../context/AuthContext";
import { API } from "../../API";


const UpdateProperty = () => {
  const { user } = useContext(AuthContext);
  const { id } = useParams();
  const navigate = useNavigate();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const response = await axios.get(`${API}/property/${id}`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        if (response.data && response.data.data) {
          setProperty(response.data.data);
        }
      } catch (error) {
        console.error("Error fetching property:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProperty();
  }, [id]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await axios.put(
        `${API}/property/update`,
        { ...property, sellerId: user.user_id },
        { headers: { Authorization: `Bearer ${user.jwt}` } }
      );
      alert("Property updated successfully!");
      navigate(`/property/${id}`);
    } catch (error) {
      console.error("Error updating property:", error);
      alert("Failed to update property");
    }
  };

  if (loading) return <div className="text-center mt-4">Loading property details...</div>;
  if (!property) return <div className="text-center mt-4">Property not found</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 text-primary">Update Property</h2>
      <form onSubmit={handleUpdate}>
        <div className="mb-3">
          <label className="form-label">Property Name</label>
          <input
            type="text"
            className="form-control"
            value={property.name}
            onChange={(e) => setProperty({ ...property, name: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Description</label>
          <textarea
            className="form-control"
            value={property.description}
            onChange={(e) => setProperty({ ...property, description: e.target.value })}
            required
          ></textarea>
        </div>
        <div className="mb-3">
          <label className="form-label">Price</label>
          <input
            type="number"
            className="form-control"
            value={property.price}
            onChange={(e) => setProperty({ ...property, price: parseFloat(e.target.value) })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Bedrooms</label>
          <input
            type="number"
            className="form-control"
            value={property.bedrooms}
            onChange={(e) => setProperty({ ...property, bedrooms: parseInt(e.target.value) })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Bathrooms</label>
          <input
            type="number"
            className="form-control"
            value={property.bathrooms}
            onChange={(e) => setProperty({ ...property, bathrooms: parseInt(e.target.value) })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Area (sqft)</label>
          <input
            type="number"
            className="form-control"
            value={property.area}
            onChange={(e) => setProperty({ ...property, area: parseFloat(e.target.value) })}
            required
          />
        </div>
        <div className="mb-3 form-check">
          <input
            type="checkbox"
            className="form-check-input"
            checked={property.furnished}
            onChange={(e) => setProperty({ ...property, furnished: e.target.checked })}
          />
          <label className="form-check-label">Furnished</label>
        </div>
        <div className="mb-3 form-check">
          <input
            type="checkbox"
            className="form-check-input"
            checked={property.parkingAvailable}
            onChange={(e) => setProperty({ ...property, parkingAvailable: e.target.checked })}
          />
          <label className="form-check-label">Parking Available</label>
        </div>
        <button type="submit" className="btn btn-primary">Update Property</button>
      </form>
    </div>
  );
};

export default UpdateProperty;
