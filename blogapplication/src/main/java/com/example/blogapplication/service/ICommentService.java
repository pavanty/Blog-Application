package com.example.blogapplication.service;

import com.example.blogapplication.entity.Comments;

import java.util.List;

public interface ICommentService {
    Comments savecomment(int blogid, Comments comments);

    List<Comments> getallcomment();



    List<Comments> getAllCommentsByBlogId(int blogid);

    Comments deletebycommentyid(int commentid);

    Comments getCommentById(int commentid);


    Comments updatecommentbyid(Integer commentid, Comments comments);


}
