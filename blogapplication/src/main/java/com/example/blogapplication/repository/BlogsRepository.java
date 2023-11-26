package com.example.blogapplication.repository;

import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogsRepository extends JpaRepository<Blogs,Integer> {

    Blogs findByTitleAndBlogCategory(String title, BlogCategory blogCategory);

    List<Blogs> findByBlogCategoryBlogcategoryid(int blogcategoryid);
}
