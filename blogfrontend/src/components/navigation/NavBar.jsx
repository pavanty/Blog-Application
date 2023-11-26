import React from "react";
import "./Navigation.css";
import blog from "./blog.jpg";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { Link, useNavigate } from "react-router-dom";
export default function NavBar({onLogout}) {
  const ondeletelocalvalue = () => {
    localStorage.removeItem("userinformation");
    navigate("/login");
 onLogout()
  };

  const navigate = useNavigate();

  const gotologinpage = () => {
    navigate("/login");
  };

  return (
    <nav
      className="navbar navbar-expand-lg navbar-dark static-top"
      style={{ backgroundColor: "#78ADA5" }}
    >
      <div className="container-fluid blogstyle">
        <a className="navbar-brand" href="#">
          <img className="blog" src={blog} alt="Blog" />
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <button onClick={gotologinpage} className="buttonsidebarclose">
                Login
              </button>
            </li>
            <li className="nav-item">
              <button className="buttonsidebarclose">Profile</button>
            </li>
            <li className="nav-item">
              <button
                onClick={ondeletelocalvalue}
                className="buttonsidebarclose"
              >
                Logout
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
