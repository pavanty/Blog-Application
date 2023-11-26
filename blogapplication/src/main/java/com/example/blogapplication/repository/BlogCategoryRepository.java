package com.example.blogapplication.repository;

import com.example.blogapplication.entity.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory,Integer> {
    boolean existsByTitle(String title);

    Optional<BlogCategory> findByTitle(String title);


    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Blogs b WHERE b.blogCategory.blogcategoryid = :blogcategoryid")
    boolean ifBlogsPresent(@Param("blogcategoryid") int blogcategoryid);


}