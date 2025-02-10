import React from "react";
import NavBar from "../components/NavBar";
import Footer from "../components/Footer";
import { Outlet } from "react-router-dom";

const RootLayout = () => {
  return (
    <>
      <NavBar />
      <div style={{ paddingTop: "80px", paddingBottom: "20px" }}>
        <Outlet />
      </div>
      <Footer />
    </>
  );
};

export default RootLayout;
