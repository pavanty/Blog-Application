package com.example.blogapplication.repository;

import com.example.blogapplication.entity.BlogAction;
import com.example.blogapplication.entity.Blogs;
import com.example.blogapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogActionRepository extends JpaRepository<BlogAction,Integer> {
    BlogAction findByRegisterAndBlogs(User user, Blogs blog);

}
