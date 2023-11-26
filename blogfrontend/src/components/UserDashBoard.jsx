import React, { useState } from "react";
import BlogCategeories from "./BlogCategeories";
import CreateCategory from "./CreateCategory";
import CreateBlogs from "./blogs/CreateBlogs";
import BlogLists from "./blogs/BlogLists";
import DeletedBlogs from "./blogs/DeletedBlogs";

export default function UserDashboard() {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [sidebarOpen, setSidebarOpen] = useState(true);

  const filterProducts = (category) => {
    setSelectedCategory(category);
  };

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  let mainContent;

  if (selectedCategory === "create_category") {
    mainContent = <CreateCategory />;
  } else if (selectedCategory === "create_blog") {
    mainContent = <CreateBlogs />;
  } else if (selectedCategory === "category_list") {
    mainContent = <BlogCategeories />;
  } else if (selectedCategory === "Blog_list") {
    mainContent = <BlogLists />;
  } else if (selectedCategory === "Deleted_Blogs") {
    mainContent = <DeletedBlogs />;
  } else {
    mainContent = <BlogCategeories />;
  }
  const stylecolor = {};
  return (
    <div className="row ">
      <div className="d-flex" style={{ minHeight: "100vh" }}>
        <div
          className="top-bar"
          style={{ height: "100%", background: "lightgray" }}
        ></div>

        <div
          className={`sidebarview sidebar-main-page col-2 ${
            sidebarOpen ? "open-sidebar shadow navbarborder" : ""
          }`}
          style={{
            height: "95%",
            top: "80px",
            height: "100%",
          }}
        >
          <button className="buttonsidebarshow" onClick={toggleSidebar}>
            &#x22EE;
          </button>
          {sidebarOpen && (
            <div>
              <h2>Lists</h2>
              <div
                className="sidebar-item"
                onClick={() => filterProducts("create_category")}
              >
                <h5>Create Category</h5>
              </div>
              <div
                className="sidebar-item"
                onClick={() => filterProducts("create_blog")}
              >
                <h5>Create Blog</h5>
              </div>
              <div
                className="sidebar-item"
                onClick={() => filterProducts("category_list")}
              >
                <h5>Category List</h5>
              </div>
              <div
                className="sidebar-item"
                onClick={() => filterProducts("Blog_list")}
              >
                <h5>Blog List</h5>
              </div>
              <div
                className="sidebar-item"
                onClick={() => filterProducts("Deleted_Blogs")}
              >
                <h5>Deleted Blogs</h5>
              </div>
            </div>
          )}
        </div>

        <div className="col-8 main-column-page" style={{ marginLeft: "200px" }}>
          {mainContent}
        </div>
      </div>
    </div>
  );
}
