import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { API } from "../../API";
import AuthContext from "../../context/AuthContext";

const ManageUsers = () => {
  const { user } = useContext(AuthContext); // logged-in admin
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [updatingUserId, setUpdatingUserId] = useState(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        // Updated endpoint to fetch all non-admin users
        const response = await axios.get(`${API}/user/non-admin-users`, {
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
  }, [user.jwt]);

  const handleToggleStatus = async (targetUser) => {
    setUpdatingUserId(targetUser.id);
    try {
      const payload = {
        userId: targetUser.id,
        adminId: user.user_id, // admin's id from context
        // Toggle the status: if currently active, set to false; if inactive, set to true
        isActive: !targetUser.isActive,
      };

      const response = await axios.put(`${API}/user/status`, payload, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });

      if (response.data && response.data.message) {
        // Update the user’s status locally without removing them from the list
        setUsers((prevUsers) =>
          prevUsers.map((u) =>
            u.id === targetUser.id ? { ...u, isActive: !targetUser.isActive } : u
          )
        );
      }
    } catch (error) {
      console.error("Error updating user status:", error);
    } finally {
      setUpdatingUserId(null);
    }
  };

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
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {users.length > 0 ? (
              users.map((userItem) => (
                <tr key={userItem.id}>
                  <td className="fw-bold">{userItem.id}</td>
                  <td>{userItem.fullName}</td>
                  <td>{userItem.mobileNumber}</td>
                  <td>
                    <span
                      className={`badge ${
                        userItem.userType === "SELLER" ? "bg-success" : "bg-info"
                      }`}
                    >
                      {userItem.userType}
                    </span>
                  </td>
                  <td>
                    {userItem.aadhaarNumber ? userItem.aadhaarNumber : "Not Provided"}
                  </td>
                  <td>
                    {userItem.isActive ? (
                      <span className="badge bg-success">Active</span>
                    ) : (
                      <span className="badge bg-danger">Inactive</span>
                    )}
                  </td>
                  <td>
                    <button
                      className={`btn btn-sm ${
                        userItem.isActive ? "btn-warning" : "btn-success"
                      }`}
                      onClick={() => handleToggleStatus(userItem)}
                      disabled={updatingUserId === userItem.id}
                    >
                      {updatingUserId === userItem.id
                        ? "Updating..."
                        : userItem.isActive
                        ? "Deactivate"
                        : "Activate"}
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="7" className="text-center">
                  No users found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ManageUsers;
