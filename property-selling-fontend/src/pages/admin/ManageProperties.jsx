import React, { useState, useEffect, useContext } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import AuthContext from "../../context/AuthContext";
import { API } from "../../API";


const ManageProperties = () => {
  const { user } = useContext(AuthContext);
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const response = await axios.get(`${API}/property/all`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        if (response.data && response.data.data) {
          setProperties(response.data.data);
        }
      } catch (error) {
        console.error("Error fetching properties:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProperties();
  }, []);

  const handleTogglePropertyStatus = async (propertyId, isActive) => {
    try {
      await axios.put(
        `${API}/property/status`,
        { propertyId, adminId: user.user_id, isActive: !isActive },
        { headers: { Authorization: `Bearer ${user.jwt}` } }
      );
      alert(`Property ${isActive ? "disabled" : "enabled"} successfully!`);
      setProperties(properties.map(prop => prop.id === propertyId ? { ...prop, isActive: !isActive } : prop));
    } catch (error) {
      console.error("Error updating property status:", error);
      alert("Failed to update property status");
    }
  };

  if (loading) return <div className="text-center mt-4">Loading properties...</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 text-primary">Manage Properties</h2>
      <div className="table-responsive">
        <table className="table table-striped table-hover shadow-sm">
          <thead className="thead-dark bg-primary text-white">
            <tr>
              <th>Name</th>
              <th>Property Type</th>
              <th>Seller Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {properties.length > 0 ? (
              properties.map((property) => (
                <tr key={property.id}>
                  <td>{property.name}</td>
                  <td>{property.propertyType}</td>
                  <td>{property.sellerName}</td>
                  <td>
                    <Link to={`/property/${property.id}`} className="btn btn-info btn-sm me-2">
                      View Details
                    </Link>
                    <button
                      className={`btn btn-${property.isActive ? "danger" : "success"} btn-sm`}
                      onClick={() => handleTogglePropertyStatus(property.id, property.isActive)}
                    >
                      {property.isActive ? "Disable" : "Enable"}
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4" className="text-center">No properties found</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ManageProperties;
