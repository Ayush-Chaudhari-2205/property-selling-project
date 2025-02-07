import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { API } from "../../API";
import AuthContext from "../../context/AuthContext";


const ManageUsers = () => {
  const { user } = useContext(AuthContext);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await axios.get(`${API}/user/active-non-admin-users`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        if (response.data && response.data.data) {
          setUsers(response.data.data);
        }
      } catch (error) {
        console.error("Error fetching users:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  if (loading) return <div className="text-center mt-4">Loading users...</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 text-primary">Manage Users</h2>
      <div className="table-responsive">
        <table className="table table-striped table-hover shadow-sm">
          <thead className="thead-dark bg-primary text-white">
            <tr>
              <th>ID</th>
              <th>Full Name</th>
              <th>Mobile Number</th>
              <th>User Type</th>
              <th>Aadhaar Card</th>
            </tr>
          </thead>
          <tbody>
            {users.length > 0 ? (
              users.map((user) => (
                <tr key={user.id}>
                  <td className="fw-bold">{user.id}</td>
                  <td>{user.fullName}</td>
                  <td>{user.mobileNumber}</td>
                  <td>
                    <span className={`badge ${user.userType === 'SELLER' ? 'bg-success' : 'bg-info'}`}>{user.userType}</span>
                  </td>
                  <td>{user.aadhaarNumber ? user.aadhaarNumber : "Not Provided"}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5" className="text-center">No users found</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ManageUsers;
