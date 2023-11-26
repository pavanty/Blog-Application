import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import axiosInstance from "./axiosInterceptor";
export default function EditBlogCategory() {
  const { categoryid } = useParams();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [blogcategoryimage, setBlogCategoryImage] = useState(null);

  const navigate = useNavigate();

  const getBlogCategoryById = () => {
    axiosInstance
      .get(
        `http://localhost:8080/blogcategory/getblogcategorybyid/${categoryid}`
      )
      .then((resp) => {
        setTitle(resp.data.title);
        setDescription(resp.data.description);

        console.log(resp.data);
      });
  };

  useEffect(() => {
    getBlogCategoryById();
  }, [categoryid]);

  const handleFileChange = (event) => {
    setBlogCategoryImage(event.target.files[0]);
  };

  const handlsubmitCreatecategory = async (e) => {
    e.preventDefault();
    const lowercaseTitle = title.toLowerCase();
    const formData = new FormData();
    formData.append("blogcategoryimage", blogcategoryimage);

    const sendnewcategory = {
      title:lowercaseTitle,
      description,
    };

    try {
      const response = await axiosInstance.put(
        `http://localhost:8080/blogcategory/editblogcategory/${categoryid}`,
        formData,
        {
          params: sendnewcategory,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      toast.success("Category Edited Successfully!");
      setTimeout(() => {
        navigate(-1);
      }, 2000);
      console.log("Success:", response.data);
    } catch (error) {
      toast.error("Title Already exists!");
    }
  };

  return (
    <div>
     
      <h2 style={{ color: "#32D59C" }}>Edit Category</h2>
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
            style={{ backgroundColor: "#32D59C" }}
            onClick={handlsubmitCreatecategory}
            className="buttonsubmit"
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
