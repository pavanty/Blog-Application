import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./Blog.css";
import axiosInstance from "../axiosInterceptor";
import UserDashBoard from "../UserDashBoard";
import { useNavigate } from "react-router-dom";
import BlogComments from "./BlogComments";

function BlogView() {
  const [loading, setLoading] = useState(true);
  const { blogid } = useParams();
  const [blogview, setblogview] = useState({});
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [sidebarOpen, setSidebarOpen] = useState(true);

  useEffect(() => {
    getblogdata();
  }, [blogid]);

  const getblogdata = () => {
    axiosInstance
      .get(`http://localhost:8080/blogs/getblogbyid/${blogid}`)
      .then((resp) => {
        setblogview(resp.data);
        setLoading(false);
      })
      .catch((err) => console.log(err));
  };
  const navigate = useNavigate();
  const filterProducts = () => {
    navigate("/userdashboard");
  };
  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  return (
    <div>
      {loading ? ( 
        <div className="loading-indicator">
          <p className="no-categories-message">Loading...</p>
        </div>
      ) : (
        <div className="row">
          <div className="d-flex" style={{ minHeight: "100vh" }}>
            <div
              className="top-bar"
              style={{ height: "100%", background: "lightgray" }}
            ></div>

            <div
              className={`sidebarview sidebar-main-page col-3 ${
                sidebarOpen ? "open-sidebar shadow navbarborder" : ""
              }`}
              style={{
                height: "95%",
                top: "80px",
                height: "calc(100%)",
              }}
            >
              <button className="buttonsidebarshow" onClick={toggleSidebar}>
                &#x22EE;
              </button>
              {sidebarOpen && (
                <div>
                  <div className=" imageviewblogcategory">
                    <img
                      className="imageblogcategory"
                      src={`data:image/jpeg;base64,${blogview?.blogCategory?.blogcategoryimage}`}
                      alt={blogview?.blogCategory?.blogcategoryimage}
                    />
                  </div>
                  <div className="blogcategoryinformation">
                    <h5>{blogview?.blogCategory?.title.toUpperCase()}</h5>
                    <h6>{blogview?.blogCategory?.creationtime}</h6>
                    <p style={{ fontFamily: "cursive", fontWeight: "bold" }}>
                      {blogview?.blogCategory?.description}
                    </p>
                  </div>

                  <div
                    className="sidebar-item"
                    onClick={() => filterProducts()}
                  >
                    <h5
                      style={{ border: "1px solid #426596", margin: "100px" }}
                    >
                      DashBoard
                    </h5>
                  </div>
                </div>
              )}
            </div>
            <div className="col-8 blogmaincontents">
              <div>
                <div className="blogcontents">
                  <h5>{blogview?.title?.toUpperCase()}</h5>
                  <div></div>
                </div>
                <div className=" imageblog">
                  <img
                    className="imageviewblog"
                    src={`data:image/jpeg;base64,${blogview?.blogimage}`}
                    alt={blogview.blogimage}
                  />
                </div>
                <div>
                  <h5>{blogview?.register?.name}</h5>
                  <p>{blogview.date}</p>
                  {/* {blogview?.blogregister?.reg_id && (
                    <p>{blogview.blogregister.reg_id}</p>
                  )} */}
                </div>
                <p className="blogcontents"> {blogview.content}</p>

                <div>
                  <BlogComments
                    blogid={blogid}
                    blogregisterid={blogview?.blogregister?.reg_id}
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default BlogView;
