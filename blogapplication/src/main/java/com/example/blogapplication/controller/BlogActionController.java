package com.example.blogapplication.controller;

import com.example.blogapplication.entity.BlogAction;
import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.entity.Comments;
import com.example.blogapplication.service.IBlogActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/blogaction")

@CrossOrigin(value = "*")
public class BlogActionController {

    @Autowired
    IBlogActionService iBlogActionService;
//
    @PostMapping("/User/{userId}/Blog/{blogId}/LikeBlog")
    public ResponseEntity<BlogAction> liketheBlog(
            @PathVariable int userId,
            @PathVariable int blogId){
        return new  ResponseEntity <BlogAction>(iBlogActionService.liketheBlog(userId,blogId), HttpStatus.CREATED);

    }

    @GetMapping(value = "/showlikedblogbyuseridnadblogid/{userId}/{blogId}")
    public ResponseEntity<BlogAction> showalllikebyuserandblogid(
            @PathVariable int userId,@PathVariable int blogId)
     {
        BlogAction likedblogs = iBlogActionService.showalllikebyuserandblogid(userId,blogId);
        return new ResponseEntity<BlogAction>(likedblogs, HttpStatus.OK);
    }
}


