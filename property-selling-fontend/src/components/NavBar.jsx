// src/components/NavBar.jsx
import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import AuthContext from "../context/AuthContext";

function NavBar() {
  const { user } = useContext(AuthContext);

  return (
    <nav className="navbar navbar-expand-lg fixed-top bg-body clean-navbar">
      <div className="container">
        <Link className="navbar-brand logo" to="/">RadientProperty</Link>
        <button data-bs-toggle="collapse" className="navbar-toggler" data-bs-target="#navcol-1">
          <span className="visually-hidden">Toggle navigation</span>
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navcol-1">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item"><Link className="nav-link active" to="/">Home</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/features">Features</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/pricing">Pricing</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/about-us">About Us</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/contact-us">Contact Us</Link></li>
            {user ? (
              <>
                <li className="nav-item"><Link className="nav-link" to="/profile">Profile</Link></li>
                <li className="nav-item"><Link className="nav-link" to="/logout">Logout</Link></li>
              </>
            ) : (
              <li className="nav-item"><Link className="nav-link" to="/login">Login</Link></li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default NavBar;
