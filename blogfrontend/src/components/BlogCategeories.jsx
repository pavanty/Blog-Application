import React, { useEffect, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import ReactPaginate from "react-paginate";
import axiosInstance from "./axiosInterceptor";
export default function BlogCategeories() {
  const [getallcategories, setGetallcategories] = useState([]);
  const [searchcategory, setSearchcategory] = useState("");
  const [userInformation, setUserInformation] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const itemsPerPage = 6;

  const itemOffset = currentPage * itemsPerPage;
  const navigate = useNavigate();

  const cretaeNewcategory = () => {
    navigate("/createcategory");
  };

  const getdata = () => {
    axiosInstance
      .get("http://localhost:8080/blogcategory/showallblogcategory")
      .then((resp) => {
        setGetallcategories([...resp.data]);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    setUserInformation(JSON.parse(localStorage.getItem("userinformation")));
    getdata();
  }, []);

  const filteredCategories = getallcategories.filter((category) =>
    category.title.toLowerCase().includes(searchcategory.toLowerCase())
  );
  const handlePageChange = (selectedPage) => {
    setCurrentPage(selectedPage.selected);
  };
  const handledeletecategory = (blogcategoryid) => {
    axiosInstance
      .delete(
        `http://localhost:8080/blogcategory/deletebyblogcategoryid/${blogcategoryid}`
      )
      .then((resp) => {
        getdata();
        toast.success("Deleted Successfully!");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // const gotoblogpages = (blogcategoryid) => {
  //   navigate(`/blogs/${blogcategoryid}`);
  // };

  const handleeditcategory = (categoryid) => {
    navigate(`/editblogcategory/${categoryid}`);
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

  return (
    <div>
      <div className="row search-main">
        <div className="col-12" style={{ textAlign: "right" }}>
          <label className="customStyles">Search</label>
          <input
            className="inputvalue search-input"
            type="text"
            value={searchcategory}
            onChange={(e) => setSearchcategory(e.target.value)}
            placeholder=""
          />
        </div>
      </div>

      <div className="container-fluid">
        <div className="row details-page">
          {filteredCategories
            .slice(itemOffset, itemOffset + itemsPerPage)
            .map((getallcategory) => (
              <div
                className="card shadow col-sm-6 col-md-6 col-lg-3"
                key={getallcategory.blogcategoryid}
              >
                <img
                
                  className="shadow"
                  src={`data:image/jpeg;base64,${getallcategory.blogcategoryimage}`}
                  alt={getallcategory.blogcategoryimage}
                />
                <div className="row">
                  <p
                    className="card-title col-6  "
                    style={{
                      whiteSpace: "nowrap",
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      maxWidth: "100ch",
                      fontWeight: "bold",
                      fontSize: "20px",
                      color: "#598B82",
                      border: "1px solid #C3DCE7",
                    }}
                  >
                    {getallcategory.title.toUpperCase()}
                  </p>

                  {userInformation?.reg_id ===
                    getallcategory?.register?.reg_id && (
                    <a
                      onClick={() =>
                        handledeletecategory(getallcategory.blogcategoryid)
                      }
                      className="deletecrossmark col-2"
                    >
                      &#10007;
                    </a>
                  )}

                  {userInformation?.reg_id ===
                    getallcategory?.register?.reg_id && (
                    <a
                      onClick={() =>
                        handleeditcategory(getallcategory.blogcategoryid)
                      }
                      className="editcrossmark col-2"
                    >
                      &#x22EE;
                    </a>
                  )}

                  <div className="row">
                    <p className="blogcategoriesstyle col-6">
                      {getallcategory.register.name.charAt(0).toUpperCase() +
                        getallcategory.register.name.slice(1)}
                    </p>
                    <p className="blogcategoriesstyle col-6">
                      {getallcategory.creationtime}
                    </p>
                  </div>
                  <div className="category-description">
                    <p>{truncateText(getallcategory.description, 5 * 35)}</p>
                  </div>
                </div>
              </div>
            ))}
        </div>
      </div>

      <ReactPaginate
        breakLabel="..."
        nextLabel="next >"
        onPageChange={handlePageChange}
        pageRangeDisplayed={6}
        pageCount={Math.ceil(filteredCategories.length / itemsPerPage)}
        previousLabel="< previous"
        renderOnZeroPageCount={null}
        containerClassName="pagination"
        activeClassName="active"
      />

      <ToastContainer />
    </div>
  );
}
