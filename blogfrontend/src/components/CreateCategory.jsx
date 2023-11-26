import React, { useState, useEffect } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Navigate, useNavigate } from "react-router-dom";
import axiosInstance from "./axiosInterceptor";
function CreateCategory() {
  const date = new Date();
  const [userinfo, setuserinfo] = useState([]);
  const [blogcategoryid, setBlogcategoryid] = useState();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [CreatedBy, setCreatedBy] = useState(userinfo.name);
  const [Modifiedby, setModified] = useState(userinfo.name);
  const [modificationtime, setModificationtime] = useState("");
  const [creationtime, setCreationtime] = useState("");
  const [blogcategoryimage, setblogcategoryimage] = useState(null);
  const navigate = useNavigate();
  const handleFileChange = (event) => {
    setblogcategoryimage(event.target.files[0]);
  };

  useEffect(() => {
    const userInformation = JSON.parse(localStorage.getItem("userinformation"));
    setuserinfo(userInformation);
    setCreatedBy(userInformation.name);
    setModified(userInformation.name);
    setModificationtime(date.toDateString());
    setCreationtime(date.toDateString());
    setBlogcategoryid(0);
  }, []);

  const handlsubmitCreatecategory = async (e) => {
  e.preventDefault();
  const lowercaseTitle = title.toLowerCase();
    const formData = new FormData();
    formData.append("blogcategoryimage", blogcategoryimage);

    const sendnewcategory = {
      title:lowercaseTitle,
      blogcategoryid,
      description,
      CreatedBy,
      Modifiedby,
      modificationtime,
      creationtime,
    };

    try {
      const response = await axiosInstance.post(
        `http://localhost:8080/blogcategory/blogcategory/${userinfo.reg_id}`,
        formData,
        {
          params: sendnewcategory,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      toast.success("Category Created Successfully!");

      console.log("Success:", response?.data);
      setTitle('')
      setDescription('');
      setblogcategoryimage('')
      setTimeout(() => {
        navigate("/userdashboard");
      }, 2000);

    } catch (error) {
      toast.error(error.response.data);
      console.log(error.response.data);
    }
  };

  const handlclear = (e) => {
    e.preventDefault();
    setTitle("");
    setDescription("");
  };

  return (
    <div>
      <h2>Create Category</h2>
      <h3>{userinfo.name}</h3>
      <div className="form-details shadow">
        <form>
          <div>
            <label>Title</label>
            <input
              className="form-control"
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Name"
            />
          </div>
          <div>
            <label>Description</label>
            <textarea
              className="form-control textarea"
              type="textarea"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Description"
            />
          </div>
          <div>
            <label>Category Image</label>
            <input
              className="form-control"
              type="file"
              id="blogcategoryimage"
              accept="image/*"
              onChange={handleFileChange}
              placeholder="Image"
            />
          </div>
          <button
            onClick={handlsubmitCreatecategory}
            style={{ backgroundColor: "#7636D1", marginRight: "10px" }}
            className="buttonsubmit"
            type="submit"
          >
            Create Category
          </button>
          <button
            onClick={handlclear}
            style={{ backgroundColor: "red" }}
            className="buttonsubmit"
            type="submit"
          >
            Cancel
          </button>
        </form>
      </div>
      <ToastContainer />
    </div>
  );
}

export default CreateCategory;
