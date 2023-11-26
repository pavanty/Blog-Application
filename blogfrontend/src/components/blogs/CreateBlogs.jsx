import React, { useState, useEffect } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../axiosInterceptor";
export default function CreateBlogs() {
  const datevalue = new Date();
  const [getallblogs, setallblogs] = useState([]);
  const [blogid, setblogid] = useState(0);
  const [title, setTitle] = useState("");
  const [content, setcontent] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedCategoryId, setSelectedCategoryId] = useState("");
  const [reg_id, setregid] = useState();
  const [blogimage, setBlogImage] = useState(null);
  const [isdeleted, setisdeleted] = useState(true);
  const [date, setddate] = useState(datevalue.toDateString());
  const [userInformation, setUserInformation] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const userInformation = JSON.parse(localStorage.getItem("userinformation"));
    setregid(userInformation.reg_id);
    getallblogsvalue();
  }, []);

  const getallblogsvalue = () => {
    axiosInstance
      .get("http://localhost:8080/blogcategory/showallblogcategory")
      .then((resp) => {
        setallblogs(resp.data);
      })
      .catch((err) => console.log(err));
  };

  const handleFileChange = (event) => {
    setBlogImage(event.target.files[0]);
  };

  const handleCategoryChange = (event) => {
    const selectedTitle = event.target.value;
    setSelectedCategory(selectedTitle);

    const selectedCategory = getallblogs.find(
      (blog) => blog.title === selectedTitle
    );
    setSelectedCategoryId(
      selectedCategory ? selectedCategory.blogcategoryid : ""
    );
    console.log(selectedCategoryId);
  };

  const handlsubmitCreatecategory = async (e) => {
    e.preventDefault();
    console.log(
      "title",
      title,
      "content",
      content,
      "selectedcategory",
      selectedCategory,
      "blogimage",
      blogimage
    );
  
    // Clear any previous error messages
    toast.dismiss();
  
    if (!title || !content || selectedCategory === "") {
      toast.error("Please fill in all required fields.");
      return;
    }
    const lowercaseTitle = title.toLowerCase();
  
    try {
      const formData = new FormData();
      formData.append("blogimage", blogimage);
  
      const sendnewcategory = {
        reg_id,
        title: lowercaseTitle,
        blogid,
        content,
        date,
        isdeleted,
      };
  
      const response = await axiosInstance.post(
        `http://localhost:8080/blogs/register/${selectedCategoryId}`,
        formData,
        {
          params: sendnewcategory,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      toast.success("Blogs Created Successfully!");
      setTitle("");
      setcontent("");
      setBlogImage(null);
      setSelectedCategory("");
      setTimeout(() => {
        navigate("/userdashboard");
      }, 2000);
  
      console.log("Success:", response.data);
    } catch (error) {
      console.log(error.data);
      toast.error("Blog with the same title already exists in this category");
    }
  };
  

  return (
    <div>
      {/* <h2>{selectedCategoryId}</h2> */}

      <h2 style={{ color: "#32D59C" }}>Create Blogs</h2>
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
            <label>Content</label>
            <textarea
              className="form-control textarea " cols="50" rows="10"
              type="textarea"
              value={content}
              onChange={(e) => setcontent(e.target.value)}
              placeholder="Content"
            />
          </div>
          <div>
            <label>Category</label>
            <select
              className="form-control"
              value={selectedCategory}
              onChange={handleCategoryChange}
            >
              <option value="">Select a category</option>
              {getallblogs.map((blog) => (
                <option key={blog.blogcategoryid} value={blog.title}>
                  {blog.title}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label>Category Image</label>
            <input
              className="form-control"
              type="file"
              id="blogimage"
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
            Create Blogs
          </button>
        </form>
      </div>
      <ToastContainer />
    </div>
  );
}
