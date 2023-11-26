package com.example.blogapplication.repository;


import com.example.blogapplication.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Integer> {
    List<Comments> findByBlogsBlogid(int blogid);
}
