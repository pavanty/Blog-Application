package com.example.blogapplication.service;

import com.example.blogapplication.entity.BlogAction;
import com.example.blogapplication.entity.Blogs;
import com.example.blogapplication.entity.User;
import com.example.blogapplication.exception.BlogNotFoundException;
import com.example.blogapplication.exception.UserNotFoundException;
import com.example.blogapplication.repository.BlogActionRepository;
import com.example.blogapplication.repository.BlogsRepository;
import com.example.blogapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogActionImpl implements IBlogActionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogsRepository blogsRepository;

    @Autowired
    BlogActionRepository blogActionRepository;

    @Override
    public BlogAction liketheBlog(int userId, int blogId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Blogs blog = blogsRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + blogId));

        BlogAction existingLike = blogActionRepository.findByRegisterAndBlogs(user, blog);

        if (existingLike == null) {
            BlogAction like = new BlogAction();
            like.setIslike(true);
            like.setLikedby(user.getName());
            like.setRegister(user);
            like.setBlogs(blog);

            return blogActionRepository.save(like);
        } else {
            blogActionRepository.delete(existingLike);
            return existingLike;
        }
    }

//    @Override
//    public List<BlogAction> showalllikebyuserandblogid(int userId, int blogId) {
//        Register user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
//
//        Blogs blog = blogsRepository.findById(blogId)
//                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + blogId));
//
//        BlogAction existingLike = blogActionRepository.findByRegisterAndBlogs(user, blog);
//
//        if (existingLike != null) {
//            return (List<BlogAction>) existingLike;
//        } else {
//
//            return null;
//        }


    @Override
    public BlogAction showalllikebyuserandblogid(int userId, int blogId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Blogs blog = blogsRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + blogId));

        BlogAction existingLike = blogActionRepository.findByRegisterAndBlogs(user, blog);

        if (existingLike != null) {
            return existingLike;
        } else {

            return null;
        }



}
    }

