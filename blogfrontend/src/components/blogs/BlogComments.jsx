import React, { useState, useEffect } from "react";
import axios from "axios";
import "./BlogComment.css";
import { ToastContainer, toast } from "react-toastify";
import { Navigate, useNavigate } from "react-router-dom";
import axiosInstance from "../axiosInterceptor";
export default function BlogComments(props) {
  const navigate = useNavigate();
  const { blogid } = props;
  const numberblogid = parseInt(blogid, 10);
  const [reg_id, setRegId] = useState("");
  const [getallcommentsdata, setAllComments] = useState([]);
  const [commentcontent, setCommentContent] = useState("");
  const [createdby, setCreatedBy] = useState("");
  const [modificationtime, setModificationTime] = useState("");
  const [creationtime, setCreationTime] = useState("");
  const [commentname, setcommentname] = useState("");
  const date = new Date();
  const [userInformation, setUserInformation] = useState(null);

  useEffect(() => {
    setUserInformation(JSON.parse(localStorage.getItem("userinformation")));
    getallcomments();
  }, []);

  useEffect(() => {
    if (userInformation) {
      setRegId(userInformation.reg_id);
      setCreatedBy(userInformation.name);
      setModificationTime(date.toDateString());
      setcommentname(userInformation.name);
      setCreationTime(date.toDateString());
    }
  }, [userInformation]);

  const getallcomments = async () => {
    try {
      const response = await axiosInstance.get(
        "http://localhost:8080/comments/getallcomments/"
      );
      setAllComments([...response.data]);
    } catch (error) {
      console.log(error);
    }
  };

  const getFilteredComments = () => {
    return getallcommentsdata.filter(
      (comment) => comment?.blogs?.blogid === numberblogid
    );
  };

  const submitcomments = (e) => {
    e.preventDefault();
    const sendcommentdata = {
      reg_id,
      commentcontent,
      createdby,
      modificationtime,
      commentname,
      creationtime,
    };
    console.log("in axios", typeof blogid);
    axiosInstance
      .post(
        `http://localhost:8080/comments/savecomments/${numberblogid}`,
        sendcommentdata
      )
      .then((resp) => {
        setCommentContent("");
        console.log("response", resp);
        toast.success("Comment Added Successfully");
        getallcomments();
      })
      .catch((err) => console.log(err));
  };
  const handledeletecategory = (commentid) => {
    axiosInstance
      .delete(`http://localhost:8080/comments/deletecommentbyid/${commentid}`)
      .then((resp) => {
        toast.error("Comment Deleted Successfully");
        getallcomments();
      });
  };

  const handleeditcategory = (commentid) => {
    navigate(`/commentupdate/${commentid}`);
  };

  const handleDeleteSelectedComments = () => {
    let deletedSuccessfully = false; 
    const filteredCartItems = getallcommentsdata.filter(
      (item) =>
        item?.blogs?.blogid === numberblogid &&
        item?.blogs?.blogregister?.reg_id == userInformation?.reg_id
    );

    filteredCartItems.forEach((item) => {
    
      axiosInstance
        .delete(
          `http://localhost:8080/comments/deletecommentbyid/${item.commentid}`
        )
        .then((resp) => {
          deletedSuccessfully = true;
          // toast.success("Comment deleted successfully");
          getallcomments();
        })
        .catch((err) => toast.error(err));
    });
    if (deletedSuccessfully) {
      toast.success("Comments deleted successfully");
    }
  };


  return (
    <div>
      <div className="comments row">
      <div className="col-7">   <h2
          style={{
            margin: "10px",
            textAlign: "left",
            marginBottom: "20px",
          }}
        >
          COMMENTS
        </h2></div>
      <div className="col-4">{userInformation &&
              props.blogregisterid &&
              userInformation.reg_id === props.blogregisterid && (
                <button
                  className="btn btn-danger " style={{marginTop:"10px"}}
                  onClick={handleDeleteSelectedComments}
                >
                  Delete All Comments
                </button>
              )}</div>
     
        
        <div className="col-10">
          <form>
            <textarea
              rows={7}
              className="form-control "
              value={commentcontent}
              onChange={(e) => setCommentContent(e.target.value)}
              placeholder="write the review about the Blog"
            ></textarea>
            <button
              type="submit"
              style={{marginTop:"10px"}}
              className="btn btn-success reviewbutton"
              onClick={submitcomments}
            >
              submit
            </button>
          </form>

          <div className="review-main">
           

            {getallcommentsdata
              .filter((getcomment) => {
                return getcomment.blogs.blogid === numberblogid;
              })
              .map((filteredComment) => (
                <div
                  className="shadow round pb-3 classdetails"
                  key={filteredComment.commentid}
                >
                  <div
                    className="title"
                    style={{
                      display: "flex",
                      justifyContent: "space-between",
                    }}
                  >
                    <div
                      style={{
                        display: "flex",
                      }}
                    >
                      <h4
                        style={{
                          paddingRight: "15px",
                        }}
                      >
                        {filteredComment?.commentname?.toUpperCase()}
                      </h4>
                      <span
                        style={{
                          color: "#9D9EA0",
                          fontSize: "12px",
                          marginTop: "5px",
                        }}
                      >
                        {filteredComment?.creationtime}
                      </span>
                    </div>
                    <div>
                      <span>
                        {userInformation &&
                          userInformation.reg_id === filteredComment.reg_id && (
                            <div
                              style={{
                                color: "#9D9EA0",
                                fontSize: "12px",
                                paddingLeft: "5px",
                              }}
                            >
                              <a
                                onClick={() =>
                                  handledeletecategory(
                                    filteredComment.commentid
                                  )
                                }
                                className="deletecrossmark col-2"
                              >
                                &#10007;
                              </a>
                              <a
                                onClick={() =>
                                  handleeditcategory(filteredComment.commentid)
                                }
                                className="editcrossmark col-2 "
                                style={{
                                  paddingLeft: "10px",
                                  color: "black",
                                }}
                              >
                                &#x22EE;
                              </a>
                            </div>
                          )}
                      </span>
                    </div>
                  </div>
                  <h3 className="comment-styling">
                    {filteredComment.commentcontent}
                  </h3>
                </div>
              ))}
          </div>
        </div>
      </div>

      <ToastContainer />
    </div>
  );
}
