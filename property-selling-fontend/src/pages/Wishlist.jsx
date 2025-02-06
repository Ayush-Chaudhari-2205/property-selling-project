// src/pages/Wishlist.jsx
import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { API } from "../API";
import AuthContext from "../context/AuthContext";
import WishlistCard from "../components/WishlistCard";

const Wishlist = () => {
  const { user } = useContext(AuthContext);
  const [wishlist, setWishlist] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchWishlist = async () => {
      try {
        const response = await axios.get(`${API}/wishlist/${user.user_id}`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        if (response.data && response.data.data) {
          setWishlist(response.data.data);
        } else {
          setWishlist([]);
        }
      } catch (error) {
        console.error("Error fetching wishlist:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchWishlist();
  }, [user.user_id]);

  if (loading) return <div className="text-center mt-4">Loading wishlist...</div>;
  if (!wishlist.length) return <div className="text-center mt-4">No properties in your wishlist</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center">My Wishlist</h2>
      <div className="row">
        {wishlist.map((property) => (
          <div className="col-md-4 mb-4" key={property.wishlistId}>
            <WishlistCard property={property} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Wishlist;