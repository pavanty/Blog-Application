import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ReactPaginate from "react-paginate";
import "./Blog.css";
import axiosInstance from "../axiosInterceptor";
export default function DeletedBlogs() {
  const [getallblogs, setallblogs] = useState([]);
  const [searchcategory, setSearchcategory] = useState("");
  const [userInformation, setUserInformation] = useState(null);
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
        setallblogs([...resp.data]);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    setUserInformation(JSON.parse(localStorage.getItem("userinformation")));
    getdata();
  }, []);

  const handleSearch = (e) => {
    setSearchcategory(e.target.value);
    setCurrentPage(0);
  };

  const filteredblogs = getallblogs.filter(
    (blog) =>
      blog.title.toLowerCase().includes(searchcategory.toLowerCase()) &&
      !blog.isdeleted
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

  const handledeletecategory = (blogid) => {
    axiosInstance
      .put(`http://localhost:8080/blogs/updateisdeleted/${blogid}`)
      .then((resp) => {
        toast.success("Added To Blog Successfully");
        getdata();
      })
      .catch((err) => {
        toast.error("Error deleting blog");
      });
  };

  return (
    <div>
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
                    <div className="col-6">
                      <p className="blogcategoriesstyle blogtitle shadow">
                        {blog.title.toUpperCase()}
                      </p>
                    </div>

                    <div className="row">
                      <p
                        className="card-title col-10"
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
                          onClick={() => handledeletecategory(blog?.blogid)}
                          className="deletecrossmark col-2
                            
                            "
                          style={{ color: "green" }}
                        >
                          &#43;
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
    </div>
  );
}
