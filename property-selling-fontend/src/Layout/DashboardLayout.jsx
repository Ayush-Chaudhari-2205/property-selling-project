import React from "react";
import { Outlet, Link } from "react-router-dom";
import "../assets/css/dashboard.css";

function DashboardLayout() {
  return (
    <div id="wrapper" className="d-flex">
      {/* Sidebar */}
      <ul className="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
        <Link className="sidebar-brand d-flex align-items-center justify-content-center" to="/dashboard">
          <div className="sidebar-brand-icon rotate-n-15">
            <i className="fas fa-laugh-wink"></i>
          </div>
          <div className="sidebar-brand-text text-white mx-3">Admin</div>
        </Link>
        <hr className="sidebar-divider" />
        <li className="nav-item">
          <Link className="nav-link" to="/dashboard/admin/users">
            <i className="fas fa-users"></i>
            <span>Manage Users</span>
          </Link>
        </li>
        <li className="nav-item">
          <Link className="nav-link" to="/dashboard/admin/properties">
            <i className="fas fa-home"></i>
            <span>Manage Properties</span>
          </Link>
        </li>
      </ul>

      {/* Content Wrapper */}
      <div id="content-wrapper" className="d-flex flex-column w-100">
        <div id="content" className="p-4">
          <Outlet />
        </div>

        {/* Footer */}
        <footer className="sticky-footer bg-white">
          <div className="container my-auto">
            <div className="copyright text-center my-auto">
              <span>© 2025 Admin Dashboard</span>
            </div>
          </div>
        </footer>
      </div>
    </div>
  );
}

export default DashboardLayout;
