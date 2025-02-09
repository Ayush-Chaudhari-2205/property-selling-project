import React, { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import AuthContext from "../../context/AuthContext";
import { API } from "../../API";

const ManagePropertyImages = () => {
  const { user } = useContext(AuthContext);
  const { id: propertyId } = useParams();
  const [images, setImages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedFile, setSelectedFile] = useState(null);

  useEffect(() => {
    const fetchImages = async () => {
      try {
        const response = await axios.get(`${API}/property/image/${propertyId}`, {
          headers: { Authorization: `Bearer ${user.jwt}` },
        });
        if (response.data && response.data.data) {
          setImages(response.data.data);
          console.log(response.data.data);
          
        }
      } catch (error) {
        console.error("Error fetching property images:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchImages();
  }, [propertyId]);

  const handleUpload = async (e) => {
    e.preventDefault();
    if (!selectedFile) return alert("Please select a file to upload");

    const formData = new FormData();
    formData.append("image", selectedFile);

    try {
      await axios.post(
        `${API}/property/image/upload/${propertyId}/seller/${user.user_id}`,
        formData,
        { headers: { Authorization: `Bearer ${user.jwt}`, "Content-Type": "multipart/form-data" } }
      );
      alert("Image uploaded successfully!");
      setSelectedFile(null);
      window.location.reload(); // Refresh to show the new image
    } catch (error) {
      console.error("Error uploading image:", error);
      alert("Failed to upload image");
    }
  };

  const handleDelete = async (imageId) => {
    try {
      await axios.delete(`${API}/property/image/delete/${imageId}/seller/${user.user_id}`, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });
      alert("Image deleted successfully!");
      setImages(images.filter(image => image.imageId !== imageId)); // Remove from state
    } catch (error) {
      console.error("Error deleting image:", error);
      alert("Failed to delete image");
    }
  };

  if (loading) return <div className="text-center mt-4">Loading images...</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 text-primary">Manage Property Images</h2>
      
      {/* Upload Form */}
      <form onSubmit={handleUpload} className="mb-4">
        <input type="file" className="form-control mb-2" onChange={(e) => setSelectedFile(e.target.files[0])} />
        <button type="submit" className="btn btn-success">Upload Image</button>
      </form>

      {/* Image Gallery */}
      <div className="row">
        {images.length > 0 ? (
          images.map((image) => {
            // Convert Base64 to Data URL
            const imageSrc = `data:image/jpeg;base64,${image.image}`;
            return (
              <div key={image.imageId} className="col-md-4 mb-3">
                <div className="card">
                  <img src={imageSrc} alt="Property" className="card-img-top" />
                  <div className="card-body text-center">
                    <button className="btn btn-danger btn-sm" onClick={() => handleDelete(image.imageId)}>
                      Delete
                    </button>
                  </div>
                </div>
              </div>
            );
          })
        ) : (
          <p className="text-center">No images uploaded yet</p>
        )}
      </div>
    </div>
  );
};

export default ManagePropertyImages;
