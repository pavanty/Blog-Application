package com.example.blogapplication.service;

import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.entity.Blogs;
import com.example.blogapplication.entity.Comments;
import com.example.blogapplication.exception.BlogCategoryNotFoundException;
import com.example.blogapplication.exception.BlogNotFoundException;
import com.example.blogapplication.exception.CommentNotFoundException;
import com.example.blogapplication.repository.BlogsRepository;
import com.example.blogapplication.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentsImpl implements  ICommentService {

    @Autowired
    BlogsRepository blogsRepository;
@Autowired
    CommentRepository commentRepository;
    @Override
    public Comments savecomment(int blogid, Comments comments) {
        Optional<Blogs> blogsOptional = blogsRepository.findById(blogid);
        if (blogsOptional.isPresent()) {
            Blogs blog = blogsOptional.get();
            comments.setBlogs(blog);

            // Assuming you have a commentsRepository for saving comments
            Comments savedComments = commentRepository.save(comments);

            return savedComments;
        } else {
            throw new BlogNotFoundException("Blog with ID " + blogid + " not found.");
        }
    }

    @Override
    public List<Comments> getallcomment() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comments> getAllCommentsByBlogId(int blogid) {
        return commentRepository.findByBlogsBlogid(blogid);
    }

    @Override
    public Comments deletebycommentyid(int commentid) {
        Optional<Comments> comment2=commentRepository.findById(commentid);
        if(comment2.isPresent()){
            commentRepository.deleteById(commentid);
        }
        else{
            throw new BlogCategoryNotFoundException("BlogCategory is not existing");
        }
        return comment2.get();
    }

    @Override
    public Comments getCommentById(int commentid) {
        return commentRepository.findById(commentid)
                .orElseThrow(() -> new CommentNotFoundException("comment not found"));

    }

    @Override
    public Comments updatecommentbyid(Integer commentid, Comments comments) {
        Optional<Comments> commentsOptional=commentRepository.findById(commentid);
        if(commentsOptional.isPresent()){
            Comments existingcategory=commentsOptional.get();
            if (Objects.nonNull(comments.getCommentcontent()) && !"".equalsIgnoreCase(comments.getCommentcontent())) {
              existingcategory.setCommentcontent(comments.getCommentcontent());
          }
            return commentRepository.save(existingcategory);
        }
        return null;
    }


}

//    @Override
//    public BlogCategory editblogcategory(int blogcategoryid, BlogCategory blogCategory) {
//        Optional<BlogCategory> optionalCategory = blogCategoryRepository.findById(blogcategoryid);
//        Optional<BlogCategory> samecategorytitle=blogCategoryRepository.findByTitle(blogCategory.getTitle());
//
//        if (optionalCategory.isPresent()) {
//            BlogCategory existingCategory = optionalCategory.get();
//
//            if (Objects.nonNull(blogCategory.getDescription()) && !"".equalsIgnoreCase(blogCategory.getDescription())) {
//                existingCategory.setDescription(blogCategory.getDescription());
//            }
//            if (Objects.nonNull(blogCategory.getTitle()) && !"".equalsIgnoreCase(blogCategory.getTitle()) && samecategorytitle.isPresent()) {
//                existingCategory.setTitle(blogCategory.getTitle());
//
//            }
//
//
//            return blogCategoryRepository.save(existingCategory);
//        }
//        return null;
//    }