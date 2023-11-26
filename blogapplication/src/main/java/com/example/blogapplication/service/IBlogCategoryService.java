package com.example.blogapplication.service;

import com.example.blogapplication.entity.BlogCategory;

import java.util.List;

public interface IBlogCategoryService {
    BlogCategory saveblogcategory(int reg_id, BlogCategory blogCategory);

    List<BlogCategory> showallBlogCategory();

    boolean isTitleExists(String title);

    BlogCategory deletebyblogcategoryid(int blogcategoryid);

//    BlogCategory updateblogcategory(Integer blogcategoryid, BlogCategory blogCategory);


    BlogCategory getBlogCategoryById(int blogcategoryid);

    BlogCategory editblogcategory(int blogcategoryid, BlogCategory blogCategory);

    BlogCategory getBlogCategoryByTitle(String title);
}
