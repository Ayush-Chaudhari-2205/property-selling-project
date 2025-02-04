import React from 'react'

function NavBar() {
  return (
     <nav className="navbar navbar-expand-lg fixed-top bg-body clean-navbar">
        <div className="container"><a className="navbar-brand logo" href="#">RadientProperty</a><button data-bs-toggle="collapse" className="navbar-toggler" data-bs-target="#navcol-1"><span className="visually-hidden">Toggle navigation</span><span className="navbar-toggler-icon"></span></button>
            <div className="collapse navbar-collapse" id="navcol-1">
                <ul className="navbar-nav ms-auto">
                    <li className="nav-item"><a className="nav-link active" href="/index.html">Home</a></li>
                    <li className="nav-item"><a className="nav-link" href="/features.html">Features</a></li>
                    <li className="nav-item"><a className="nav-link" href="/pricing.html">Pricing</a></li>
                    <li className="nav-item"><a className="nav-link" href="/about-us.html">About Us</a></li>
                    <li className="nav-item"><a className="nav-link" href="/contact-us">Contact Us</a></li>
                    <li className="nav-item"><a className="nav-link" href="/login">Login</a></li>
                </ul>
            </div>
        </div>
    </nav> 
     )
}

export default NavBar