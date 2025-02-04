// src/pages/Home.jsx
import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <main className="page">
      <section
        className="clean-block clean-hero"
        style={{
          backgroundImage: "url('/img/tech/image4.jpg')",
          color: "rgba(9, 162, 255, 0.85)",
        }}
      >
        <div className="text">
          <h2>Lorem ipsum dolor sit amet.</h2>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna, dignissim nec auctor in, mattis vitae leo.
          </p>
          <button className="btn btn-outline-light btn-lg" type="button">
            Learn More
          </button>
        </div>
      </section>
      <section className="clean-block clean-info dark">
        <div className="container">
          <div className="block-heading">
            <h2 className="text-info">Info</h2>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna, dignissim nec auctor in, mattis vitae leo.
            </p>
          </div>
          <div className="row align-items-center">
            <div className="col-md-6">
              <img className="img-thumbnail" src="/assets/img/scenery/image5.jpg" alt="Scenery" />
            </div>
            <div className="col-md-6">
              <h3>Lorem impsum dolor sit amet</h3>
              <p>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Lorem ipsum dolor sit amet, consectetur adipisicing elit.
              </p>
              <button className="btn btn-outline-primary btn-lg" type="button">
                Join Now
              </button>
            </div>
          </div>
        </div>
      </section>
      <section className="clean-block features">
        <div className="container">
          <div className="block-heading">
            <h2 className="text-info">Features</h2>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna, dignissim nec auctor in, mattis vitae leo.
            </p>
          </div>
          <div className="row justify-content-center">
            <div className="col-md-5 feature-box">
              <i className="icon-star icon"></i>
              <h4>Bootstrap 5</h4>
              <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna, dignissim nec auctor in, mattis vitae leo.
              </p>
            </div>
            <div className="col-md-5 feature-box">
              <i className="icon-pencil icon"></i>
              <h4>Customizable</h4>
              <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna, dignissim nec auctor in, mattis vitae leo.
              </p>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
};

export default Home;
