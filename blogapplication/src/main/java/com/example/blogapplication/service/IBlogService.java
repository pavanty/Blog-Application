package com.example.blogapplication.service;

import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.entity.Blogs;

import java.util.List;

public interface IBlogService {
    Blogs saveblog(int blogcategoryId, Blogs blogs);

    List<Blogs> showallBlogs();

    Blogs deletebyblogid(int blogid);


    Blogs getBlogById(int blogid);

    Blogs updateisdeleted(Integer blogId);

    boolean isTitleExists(String title);

    Blogs editblogs(int blogid, Blogs existingCategory);

    boolean hasBlogsForCategory(int blogcategoryid);

    Blogs getBlogByTitleAndCategory(String title, BlogCategory blogCategory);

//    BlogCategory editblogs(int blogid, Blogs existingCategory);
}
