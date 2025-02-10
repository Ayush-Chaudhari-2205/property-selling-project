// src/pages/Home.jsx
import React from "react";
import { Link } from "react-router-dom";
import bgImage from "../assets/property-hero-image.jpg"

const Home = () => {
  return (
    <main className="page">
      <section
        className="clean-block clean-hero"
        style={{
          backgroundImage: `url(${bgImage})`, // Ensure you have a proper image in this path
          color: "rgba(255, 255, 255, 0.3)",
          backgroundSize : "cover",
          backgroundPosition : "center",
          minHeight : "70vh"
        }}
      >
        <div className="text">
          <h2>Welcome to Radiant Properties</h2>
          <p>
            Discover your dream home with our exclusive listings and expert guidance.
            Experience a new standard in property buying and selling.
          </p>
          <Link to="/properties" className="btn btn-outline-light btn-lg">
            Browse Listings
          </Link>
        </div>
      </section>
      <section className="clean-block clean-info dark">
        <div className="container">
          <div className="block-heading">
            <h2 className="text-info">About Us</h2>
            <p>
              At Radiant Properties, we connect you with the finest properties on the market.
              Our dedicated team ensures a smooth and personalized experience for every client.
            </p>
          </div>
          <div className="row align-items-center">
            <div className="col-md-6">
              <img 
                className="img-thumbnail" 
                src="/assets/img/properties/house_exterior.jpg" 
                alt="Beautiful Property" 
              />
            </div>
            <div className="col-md-6">
              <h3>Your Perfect Home Awaits</h3>
              <p>
                Whether you're looking to buy, sell, or invest, Radiant Properties offers a curated selection of premium homes and commercial spaces tailored to your needs.
              </p>
              <Link to="/contact" className="btn btn-outline-primary btn-lg">
                Contact Us
              </Link>
            </div>
          </div>
        </div>
      </section>
      <section className="clean-block features">
        <div className="container">
          <div className="block-heading">
            <h2 className="text-info">Why Choose Us</h2>
            <p>
              Our commitment to excellence sets us apart in the property market. Discover the benefits of partnering with Radiant Properties.
            </p>
          </div>
          <div className="row justify-content-center">
            <div className="col-md-5 feature-box">
              <i className="icon-home icon"></i>
              <h4>Expert Guidance</h4>
              <p>
                Benefit from years of experience and personalized support from our professional agents.
              </p>
            </div>
            <div className="col-md-5 feature-box">
              <i className="icon-list icon"></i>
              <h4>Wide Range of Listings</h4>
              <p>
                Explore an extensive collection of residential and commercial properties tailored to your lifestyle.
              </p>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
};

export default Home;
