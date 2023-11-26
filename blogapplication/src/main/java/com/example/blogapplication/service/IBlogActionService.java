package com.example.blogapplication.service;

import com.example.blogapplication.entity.BlogAction;

public interface IBlogActionService {
    BlogAction liketheBlog(int userId, int blogId);

    BlogAction showalllikebyuserandblogid(int userId, int blogId);

//    BlogAction showalllikebyuserandblogid(int userId, int blogId);
//    BlogAction likeBlog(int userId, int blogId);
}
