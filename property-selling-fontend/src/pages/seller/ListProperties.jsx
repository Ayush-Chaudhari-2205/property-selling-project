import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import AuthContext from "../../context/AuthContext";
import { API } from "../../API";
import PropertyCard from "../../components/PropertyCard";

const ListProperties = () => {
  const { user } = useContext(AuthContext);
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Wait until user is available before fetching properties
    if (!user || !user.user_id || !user.jwt) {
      console.log("User not available yet, waiting...");
      return;
    }

    const fetchProperties = async () => {
      try {
        console.log("Fetching properties for user:", user);
        
        const response = await axios.get(`${API}/property/seller/${user.user_id}`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });

        // Extract the "data" field from the API response
        if (response.data && response.data.data) {
          console.log("Properties fetched:", response.data.data);
          setProperties(response.data.data);
        } else {
          setProperties([]);
        }
      } catch (error) {
        console.error("Error fetching properties:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProperties();
  }, [user]); // Add user as a dependency to wait until it is available

  if (loading) return <div className="text-center mt-4">Loading...</div>;
  if (!user) return <div className="text-center mt-4">User not logged in</div>;
  if (properties.length === 0) return <div className="text-center mt-4">No properties found</div>;

  return (
    <div className="container mt-4">
      <h2 className="mb-4 text-center">My Listed Properties</h2>
      <div className="row">
        {properties.map((property) => (
          <div className="col-md-4 mb-4" key={property.id}>
            <PropertyCard property={property} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default ListProperties;
