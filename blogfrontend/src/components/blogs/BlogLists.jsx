import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ReactPaginate from "react-paginate";
import "./Blog.css";
import axiosInstance from "../axiosInterceptor";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart } from "@fortawesome/free-solid-svg-icons";

export default function BlogLists() {
  const [loading, setLoading] = useState(true);
  const [likedBlogs, setLikedBlogs] = useState([]);
  const [getblogids, setblogids] = useState([]);
  const [getallblogs, setallblogs] = useState([]);
  const [searchcategory, setSearchcategory] = useState("");
  const [userInformation, setUserInformation] = useState(null);
  const getalllikedblogs = async () => {
    const likedBlogsArray = [];
    try {
      for (const blogId of getblogids) {
        const response = await axiosInstance.get(
          `http://localhost:8080/blogaction/showlikedblogbyuseridnadblogid/${userInformation.reg_id}/${blogId}`
        );
        if (response.data) {
          const likedBlogData = response.data;
          console.log("liked array", response.data);
          likedBlogsArray.push(likedBlogData);
        }
      }
      setLikedBlogs([...likedBlogsArray]);
    } catch (error) {
      console.log(error);
    }
  };
  const navigate = useNavigate();

  const itemsPerPage = 6;
  const [currentPage, setCurrentPage] = useState(0);
  const itemOffset = currentPage * itemsPerPage;

  const handlePageChange = (selectedPage) => {
    setCurrentPage(selectedPage.selected);
  };

  const getdata = () => {
    axiosInstance
      .get("http://localhost:8080/blogs/getallblogs")
      .then((resp) => {
        const blogs = [...resp.data];
        getalllikedblogs();
        setallblogs(blogs);

        setLoading(false);
        const blogIds = blogs.map((blog) => blog.blogid);
        setblogids(blogIds);
      })
      .catch((err) => console.log(err));
  };

  const sendlike = (blogid) => {
    axiosInstance
      .post(
        `http://localhost:8080/blogaction/User/${userInformation?.reg_id}/Blog/${blogid}/LikeBlog`
      )
      .then((resp) => {
        getalllikedblogs();
        console.log("send data", resp.data);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    setUserInformation(JSON.parse(localStorage.getItem("userinformation")));
    getalllikedblogs().then(() => {
      getdata();
    });
  }, []);

  const handleSearch = (e) => {
    setSearchcategory(e.target.value);
    setCurrentPage(0);
  };



  
  const filteredblogs = getallblogs.filter((blog) =>
    blog.title.toLowerCase().includes(searchcategory.toLowerCase())
  );

  
  const gotoblogviewpage = (blogid) => {
    navigate(`/blogview/${blogid}`);
  };

  const customStyles = {
    color: "#78ADA5",
    marginRight: "10px",
    fontFamily: "cursive",
  };

  const truncateText = (text, maxLength) => {
    if (text.length > maxLength) {
      return text.slice(0, maxLength) + "...";
    }
    return text;
  };
  const handleeditcategory = (blogid) => {
    navigate(`/blogedit/${blogid}`);
  };

  const handledeletecategory = (blogid) => {
    axiosInstance
      .put(`http://localhost:8080/blogs/updateisdeleted/${blogid}`)
      .then((resp) => {
        toast.success("Deleted Successfully");
        getdata();
      })
      .catch((err) => {
        toast.error("Error deleting blog");
      });
  };

  return (
    <div>
      {loading ? (
        <div className="loading-indicator">
          <p className="no-categories-message">Loading...</p>
        </div>
      ) : (
        <>
          <>
            <div className="row search-main ">
              <div className="col-12" style={{ textAlign: "right" }}>
                <label style={customStyles}>Search</label>
                <input
                  className="inputvalue search-input"
                  type="text"
                  value={searchcategory}
                  onChange={handleSearch}
                  placeholder=""
                />
              </div>
            </div>
            <div className="container-fluid">
              <div className="row details-page">
                {filteredblogs.length === 0 ? (
                  <p className="no-categories-message">No Blogs available.</p>
                ) : (
                  filteredblogs
                    .slice(itemOffset, itemOffset + itemsPerPage)
                    .map((blog) => {
                      if (!blog.isdeleted) {
                        return null; // Skip rendering
                      }

                      return (
                        <div
                          className="card shadow col-sm-6 col-md-6 col-lg-3"
                          key={blog.blogid}
                        >
                          <img
                            onClick={() => gotoblogviewpage(blog.blogid)}
                            className="shadow"
                            src={`data:image/jpeg;base64,${blog.blogimage}`}
                            alt={blog.blogimage}
                          />
                          <div className="row">
                            <div className="col-10">
                              <p className="blogcategoriesstyle blogtitle shadow">
                                {blog.title.toUpperCase()}
                              </p>
                            </div>
                            <div className="col-2">
                              <FontAwesomeIcon
                                onClick={() => sendlike(blog.blogid)}
                                style={{
                                  color: likedBlogs.some(
                                    (likedBlog) =>
                                      userInformation?.reg_id ===
                                        likedBlog.blogactionregister.reg_id &&
                                      likedBlog.blogidfromaction.blogid ===
                                        blog.blogid
                                  )
                                    ? "yellow"
                                    : "white",
                                  stroke: "black",
                                  strokeWidth: "4px",
                                }}
                                icon={faHeart}
                              />
                            </div>
                          </div>

                          <div className="row">
                            <p
                              className="card-title col-5"
                              style={{
                                whiteSpace: "nowrap",
                                overflow: "hidden",
                                textOverflow: "ellipsis",
                                maxWidth: "100ch",
                                fontWeight: "bold",
                                fontSize: "20px",
                                color: "#598B82",
                              }}
                            >
                              {blog.blogCategory.title.toUpperCase()}
                            </p>

                            {userInformation?.reg_id ===
                              blog?.blogregister?.reg_id && (
                              <a
                                onClick={() =>
                                  handledeletecategory(blog?.blogid)
                                }
                                className="deletecrossmark col-3"
                              >
                                &#10007;
                              </a>
                            )}

                            {userInformation?.reg_id ===
                              blog.blogregister?.reg_id && (
                              <a
                                onClick={() => handleeditcategory(blog.blogid)}
                                className="editcrossmark col-2"
                              >
                                &#x22EE;
                              </a>
                            )}
                          </div>

                          <p className="card-title ">
                            {truncateText(blog.content, 2 * 35)}
                          </p>
                        </div>
                      );
                    })
                )}
              </div>
            </div>
          </>
          <div className="reactpaginate">
            <ReactPaginate
              breakLabel="..."
              nextLabel="next >"
              onPageChange={handlePageChange}
              pageRangeDisplayed={6}
              pageCount={Math.ceil(filteredblogs.length / itemsPerPage)}
              previousLabel="< previous"
              renderOnZeroPageCount={null}
              containerClassName="pagination"
              activeClassName="active"
            />
          </div>
          <ToastContainer />
        </>
      )}
    </div>
  );
}
