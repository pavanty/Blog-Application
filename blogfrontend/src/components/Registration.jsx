import React, { useState } from "react";
import "./RegistrationLogin.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
export default function Registration() {
  const [name, setname] = useState("");
  const [email, setemail] = useState("");
  const [mobilenumber, setmobilenumber] = useState("");
  const [password, setpassword] = useState("");
  const [error, seterror] = useState("");
  const [servermessage, setServermessage] = useState("");
  const handlsubmitregister = (e) => {
    e.preventDefault();

    if (!name) {
      toast.error("Name is required");
      return;
    }
    if (!email) {
      toast.error("Email is required");
      return;
    }
    if (!mobilenumber) {
      toast.error("Mobile Number is required");
      return;
    }
    if (!password) {
      toast.error("Password is required");
      return;
    }
    const sendregistrationdata = { name, email, mobilenumber, password };

    axios
      .post("http://localhost:8080/register", sendregistrationdata)
      .then((resp) => {
        console.log(resp.message);
        setServermessage("Registered Successfully");
        setTimeout(() => {
          gotologinbutton();
        }, 2000);
      })
      .catch((error) => {
        seterror(error.message);
        setServermessage("Registration Failed Email Already Registered");
      });
  };

  const navigate = useNavigate();
  const gotologinbutton = () => {
    navigate("/login");
  };
  return (
    <div>
      <div className="mainbutton">
        <button onClick={gotologinbutton} className="buttonmain">
          User Login
        </button>
      </div>
      <h2>Sign Up</h2>
      <h3>{servermessage}</h3>
      <div className="form-details shadow">
        <form>
          <div>
            <label>User Name</label>
            <input
              className="form-control"
              type="name"
              value={name}
              onChange={(e) => setname(e.target.value)}
              placeholder="Name"
            />
          </div>
          <div>
            <label>E-Mail</label>
            <input
              className="form-control"
              type="email"
              value={email}
              onChange={(e) => setemail(e.target.value)}
              placeholder="Email Address"
            />
          </div>
          <div>
            <label>Mobile Number</label>
            <input
              className="form-control"
              type="text"
              value={mobilenumber}
              onChange={(e) => setmobilenumber(e.target.value)}
              placeholder="Mobile Number"
            />
          </div>
          <div>
            <label>Password</label>
            <input
              className="form-control"
              type="text"
              value={password}
              onChange={(e) => setpassword(e.target.value)}
              placeholder="Password"
            />
          </div>
          <button
            onClick={handlsubmitregister}
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
