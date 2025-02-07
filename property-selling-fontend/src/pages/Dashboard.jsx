import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { API } from "../API";
import AuthContext from "../context/AuthContext";

const AdminDashboard = () => {
  const { user } = useContext(AuthContext);
  const [userCount, setUserCount] = useState(0);
  const [propertyCount, setPropertyCount] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const userResponse = await axios.get(`${API}/user/count`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        const propertyResponse = await axios.get(
          `${API}/property/count/active`,
          {
            headers: { Authorization: `Bearer ${user.jwt}` },
          }
        );

        // Correctly extract the count from "data" field
        if (userResponse.data && userResponse.data.data !== undefined) {
          setUserCount(userResponse.data.data);
        }
        if (propertyResponse.data && propertyResponse.data.data !== undefined) {
          setPropertyCount(propertyResponse.data.data);
        }
      } catch (error) {
        console.error("Error fetching admin stats:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  if (loading)
    return <div className="text-center mt-4">Loading dashboard...</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center">Admin Dashboard</h2>
      <div className="row">
        <div className="col-md-6">
          <div className="card shadow-sm p-3">
            <h5 className="card-title">Total Users</h5>
            <h3>{userCount}</h3>
          </div>
        </div>
        <div className="col-md-6">
          <div className="card shadow-sm p-3">
            <h5 className="card-title">Total Properties</h5>
            <h3>{propertyCount}</h3>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
