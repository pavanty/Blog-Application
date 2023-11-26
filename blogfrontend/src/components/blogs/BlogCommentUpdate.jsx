import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../axiosInterceptor";
export default function BlogCommentUpdate() {
  const [commentcontent, setcomment] = useState("");
  const { commentid } = useParams();
  const navigate = useNavigate();
  const handlecommentupdate = (e) => {
    e.preventDefault();

    axiosInstance
      .put(`http://localhost:8080/comments/updatecommentbyid/${commentid}`, {
        commentcontent: commentcontent,
      })
      .then((resp) => {
        toast.success("Comment Updated SuccessFully");
        setTimeout(() => {
          navigate(-1);
        }, 2000);
      })
      .catch((error) => {
        console.error("Error updating comment:", error);
      });
  };

  const getcommentbyid = () => {
    axiosInstance
      .get(`http://localhost:8080/comments/getcommentbyid/${commentid}`)
      .then((resp) => {
        setcomment(resp.data.commentcontent);
        console.log("commentdata", resp.data.commentcontent);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getcommentbyid();
  }, []);

  return (
    <div>

      <div>
        <h2 style={{ textAlign: "center" }}>BLOG Comment Page</h2>
      </div>
      <div>
        <form>
          <div className="formcommentupdate">
            <label>Comment</label>
            <textarea
              className="form-control textarea "
              cols="30"
              rows="5"
              type="textarea"
              value={commentcontent}
              onChange={(e) => setcomment(e.target.value)}
              placeholder="Content"
            />
            <div className="commentbuttonsubmit">
              {" "}
              <button className="btn btn-primary" onClick={handlecommentupdate}>
                Submit
              </button>
            </div>
          </div>
        </form>
      </div>
      <ToastContainer />
    </div>
  );
}
