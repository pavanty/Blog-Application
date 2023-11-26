package com.example.blogapplication.controller;

import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.Comments;
import com.example.blogapplication.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comments")

@CrossOrigin(value = "*")
public class CommentsController {
    @Autowired
    private ICommentService icommentService;

    @PostMapping("/savecomments/{blogid}")
    public ResponseEntity<?> savecomment(@PathVariable int blogid, @RequestBody Comments comments){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user"); // Return an Unauthorized message
        }

        UserDto userDto = (UserDto) authentication.getPrincipal();
        if (!userDto.getReg_id().equals(comments.getReg_id())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized user"); // Return a Forbidden message
        }
        return new ResponseEntity<Comments>(icommentService.savecomment(blogid, comments), HttpStatus.CREATED);
    }



    @GetMapping("/getallcomments/")
    public ResponseEntity<List<Comments>> getallcomment(){
        List<Comments> allcomments=icommentService.getallcomment();
        return new ResponseEntity<List<Comments>>(allcomments, HttpStatus.OK);
    }
    @GetMapping("/getallcommentsbyid/{blogid}")
    public ResponseEntity<List<Comments>> getAllCommentsByBlogId(@PathVariable int blogid) {
        List<Comments> allCommentsByBlogId = icommentService.getAllCommentsByBlogId(blogid);
        return new ResponseEntity<>(allCommentsByBlogId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletecommentbyid/{commentid}")
    public ResponseEntity<String> deletebycommentyid(@PathVariable int commentid) {
        icommentService.deletebycommentyid(commentid);
        String msg = "Comment Deleted Successfully";
        return new ResponseEntity<String>(msg, HttpStatus.OK);

    }
    @GetMapping(value = "/getcommentbyid/{commentid}")
    public ResponseEntity<Comments> getCommentById(@PathVariable int commentid) {
        Comments comments = icommentService.getCommentById(commentid);
        return new ResponseEntity<Comments>(comments, HttpStatus.OK);
    }
    @PutMapping("updatecommentbyid/{commentid}")
    public ResponseEntity<Comments> updatecommentbyid(@PathVariable Integer commentid, @RequestBody Comments comments) {
        return new ResponseEntity<Comments>(icommentService.updatecommentbyid(commentid,comments), HttpStatus.OK);
    }
    }



