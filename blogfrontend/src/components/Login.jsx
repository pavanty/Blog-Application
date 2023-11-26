import React, { useContext, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import { DataContext } from "./DataContext/DataContext";
export default function Login({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [serverMessage, setServerMessage] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const validateForm = () => {
    let isValid = true;

    if (!email) {
      setEmailError("Email is required");
      isValid = false;
    } else {
      setEmailError("");
    }

    if (!password) {
      setPasswordError("Password is required");
      isValid = false;
    } else {
      setPasswordError("");
    }

    return isValid;
  };

  const handlelogin = (e) => {
    e.preventDefault();
    if (validateForm()) {
      const sendlogindetails = { email, password };
      axios
        .post("http://localhost:8080/login", sendlogindetails)
        .then((resp) => {
          console.log(typeof JSON.stringify(resp.data));
          setServerMessage("Login Successfully");
          localStorage.setItem("userinformation", JSON.stringify(resp.data));

          toast.success("Login Successful!");
          onLogin();
          setTimeout(() => {
            gotoblogcategories();
          }, 2000);
        })
        .catch((error) => {
          toast.error("Login Failed!");
          setServerMessage("Login Failed");
        });
    } else {
      toast.error("Please fill in all required fields");
    }
  };

  const navigate = useNavigate();
  const gotoregisterpage = () => {
    navigate("/");
  };

  const gotoblogcategories = () => {
    navigate("/userdashboard");
  };

  return (
    <div>
      <div className="mainbutton">
        <button onClick={gotoregisterpage} className="buttonmain">
          Register
        </button>
      </div>
      <h2>Log In</h2>
      <h3>{serverMessage}</h3>
      <div className="form-details shadow">
        <form>
          <div>
            <label>E-Mail</label>
            <input
              className="form-control"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Email Address"
            />
            <span className="error-message">{emailError}</span>
          </div>
          <div>
            <label>Password</label>
            <input
              className="form-control"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Password"
            />
            <span className="error-message">{passwordError}</span>
          </div>
          <button
            onClick={handlelogin}
            style={{ backgroundColor: "#7636D1" }}
            className="buttonsubmits"
            type="submit"
          >
            Submit
          </button>
        </form>
      </div>
      <ToastContainer />
    </div>
  );
}