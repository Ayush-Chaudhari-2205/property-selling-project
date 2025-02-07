import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { API } from "../../API";
import AuthContext from "../../context/AuthContext";


const ManageInquiries = () => {
  const { user } = useContext(AuthContext);
  const [inquiries, setInquiries] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchInquiries = async () => {
      try {
        const response = await axios.get(`${API}/inquiry/seller/${user.user_id}`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        if (response.data && response.data.data) {
          setInquiries(response.data.data);
        }
      } catch (error) {
        console.error("Error fetching inquiries:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchInquiries();
  }, []);

  const handleRespond = async (inquiryId) => {
    try {
      await axios.put(
        `${API}/inquiry/respond`,
        { inquiryId, sellerId: user.user_id, responded: true },
        { headers: { Authorization: `Bearer ${user.jwt}` } }
      );
      alert("Inquiry marked as responded!");
      setInquiries(inquiries.map(inq => inq.inquiryId === inquiryId ? { ...inq, responded: true } : inq));
    } catch (error) {
      console.error("Error responding to inquiry:", error);
      alert("Failed to respond to inquiry");
    }
  };

  if (loading) return <div className="text-center mt-4">Loading inquiries...</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 text-primary">Manage Inquiries</h2>
      <div className="table-responsive">
        <table className="table table-striped table-hover shadow-sm">
          <thead className="thead-dark bg-primary text-white">
            <tr>
              <th>Property Name</th>
              <th>Buyer Name</th>
              <th>Buyer Email</th>
              <th>Message</th>
              <th>Responded</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {inquiries.length > 0 ? (
              inquiries.map((inquiry) => (
                <tr key={inquiry.inquiryId}>
                  <td>{inquiry.propertyName}</td>
                  <td>{inquiry.buyerName}</td>
                  <td>{inquiry.buyerEmail}</td>
                  <td>{inquiry.message}</td>
                  <td>{inquiry.responded ? "Yes" : "No"}</td>
                  <td>
                    {!inquiry.responded && (
                      <button
                        className="btn btn-success btn-sm"
                        onClick={() => handleRespond(inquiry.inquiryId)}
                      >
                        Mark as Responded
                      </button>
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="6" className="text-center">No inquiries found</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ManageInquiries;
