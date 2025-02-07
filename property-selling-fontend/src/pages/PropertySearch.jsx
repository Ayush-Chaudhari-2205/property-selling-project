import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import PropertyCard from "../components/PropertyCard";
import { API } from "../API";
import AuthContext from "../context/AuthContext";

const PropertySearch = () => {
  const { user } = useContext(AuthContext);
  const [properties, setProperties] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [filters, setFilters] = useState({
    type: "",
    minPrice: "",
    maxPrice: "",
    city: "",
    state: "",
    furnished: "",
  });

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = async () => {
    try {
      const response = await axios.get(`${API}/property/all-active`, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });
      setProperties(response.data.data);
    } catch (error) {
      console.error("Error fetching properties:", error);
    }
  };

  const handleSearch = async () => {
    try {
      const response = await axios.get(`${API}/property/search`, {
        params: { name: searchTerm },
        headers: { Authorization: `Bearer ${user.jwt}` },
      });
      setProperties(response.data.data);
    } catch (error) {
      console.error("Search failed:", error);
    }
  };

  const handleFilter = async () => {
    try {
      let queryParams = [];
      if (filters.type) queryParams.push(`type=${filters.type}`);
      if (filters.minPrice && filters.maxPrice) queryParams.push(`minPrice=${filters.minPrice}&maxPrice=${filters.maxPrice}`);
      if (filters.city) queryParams.push(`city=${filters.city}`);
      if (filters.state) queryParams.push(`state=${filters.state}`);
      if (filters.furnished !== "") queryParams.push(`furnished=${filters.furnished}`);

      const queryString = queryParams.length ? `?${queryParams.join("&")}` : "";
      const response = await axios.get(`${API}/property/filter${queryString}`, {
        headers: { Authorization: `Bearer ${user.jwt}` },
      });
      setProperties(response.data.data);
    } catch (error) {
      console.error("Filtering failed:", error);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center">Find Your Dream Property</h2>

      {/* Search Bar */}
      <div className="row mb-3">
        <div className="col-md-6">
          <input
            type="text"
            className="form-control"
            placeholder="Search by name..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <div className="col-md-2">
          <button className="btn btn-primary" onClick={handleSearch}>Search</button>
        </div>
      </div>

      {/* Filters */}
      <div className="row mb-3">
        <div className="col-md-3">
          <select className="form-control" value={filters.type} onChange={(e) => setFilters({ ...filters, type: e.target.value })}>
            <option value="">All Types</option>
            <option value="HOUSE">House</option>
            <option value="APARTMENT">Apartment</option>
            <option value="VILLA">Villa</option>
          </select>
        </div>

        <div className="col-md-2">
          <input type="number" className="form-control" placeholder="Min Price" value={filters.minPrice} onChange={(e) => setFilters({ ...filters, minPrice: e.target.value })} />
        </div>

        <div className="col-md-2">
          <input type="number" className="form-control" placeholder="Max Price" value={filters.maxPrice} onChange={(e) => setFilters({ ...filters, maxPrice: e.target.value })} />
        </div>

        <div className="col-md-2">
          <input type="text" className="form-control" placeholder="City" value={filters.city} onChange={(e) => setFilters({ ...filters, city: e.target.value })} />
        </div>

        <div className="col-md-2">
          <input type="text" className="form-control" placeholder="State" value={filters.state} onChange={(e) => setFilters({ ...filters, state: e.target.value })} />
        </div>

        <div className="col-md-2">
          <select className="form-control" value={filters.furnished} onChange={(e) => setFilters({ ...filters, furnished: e.target.value })}>
            <option value="">Furnished?</option>
            <option value="true">Yes</option>
            <option value="false">No</option>
          </select>
        </div>

        <div className="col-md-2">
          <button className="btn btn-secondary" onClick={handleFilter}>Apply Filters</button>
        </div>
      </div>

      {/* Property List */}
      <div className="row">
        {properties.length > 0 ? properties.map((property) => (
          <div className="col-md-4 mb-4" key={property.id}>
            <PropertyCard property={property} />
          </div>
        )) : <p className="text-center">No properties found.</p>}
      </div>
    </div>
  );
};

export default PropertySearch;
