package com.example.blogapplication.controller;

import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.*;
import com.example.blogapplication.exception.BlogExistsExpection;
import com.example.blogapplication.repository.BlogCategoryRepository;
import com.example.blogapplication.repository.BlogsRepository;
import com.example.blogapplication.repository.UserRepository;
import com.example.blogapplication.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/blogs")

@CrossOrigin(value = "*")
public class BlogsController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private BlogCategoryRepository blogCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogsRepository blogsRepository;
    @PostMapping(value = "/register/{blogcategory_id}")
    public ResponseEntity<String> saveBlog(
            @PathVariable int blogcategory_id,
            @RequestParam int reg_id,
            @RequestParam String title,
            @RequestParam Integer blogid,
            @RequestParam String content,
            @RequestParam MultipartFile blogimage,
            @RequestParam String date,
            @RequestParam boolean isdeleted) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        UserDto userDto = (UserDto) authentication.getPrincipal();
        if (!userDto.getReg_id().equals(reg_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have access to this resource");
        }
        if (blogimage == null || blogimage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blog image is required");
        }
        Optional<BlogCategory> blogCategoryOptional = blogCategoryRepository.findById(blogcategory_id);
        if (blogCategoryOptional.isPresent()) {
            BlogCategory blogCategory = blogCategoryOptional.get();
            Blogs existingBlogInCategory = blogsRepository.findByTitleAndBlogCategory(title, blogCategory);
            if (existingBlogInCategory != null && existingBlogInCategory.getBlogCategory().equals(blogCategory)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blog with the same title already exists in this category");
            }
        }
        Blogs newBlog = new Blogs();
        newBlog.setBlogid(blogid);
        newBlog.setTitle(title);
        newBlog.setContent(content);
        try {
            newBlog.setBlogimage(blogimage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newBlog.setDate(date);
        newBlog.setIsdeleted(isdeleted);

        User register = userRepository.findById(reg_id)
                .orElseThrow(() -> new RuntimeException("Register not found"));

        newBlog.setRegister(register);

        Blogs savedBlog = blogService.saveblog(blogcategory_id, newBlog);

        return ResponseEntity.status(HttpStatus.CREATED).body("Blog saved successfully");
    }


    @PutMapping("updateisdeleted/{blogId}")
    public ResponseEntity<Blogs> updateisdeleted(@PathVariable Integer blogId) {
      return new ResponseEntity<Blogs>(blogService.updateisdeleted(blogId), HttpStatus.OK);
    }

    @GetMapping(value = "getallblogs")
    public ResponseEntity<List<Blogs>> showallBlogs() {
        List<Blogs> allBlogs = blogService.showallBlogs();
        return new ResponseEntity<List<Blogs>>(allBlogs, HttpStatus.OK);
    }



    @DeleteMapping(value = "/deletebyblogid/{blogid}")
    public ResponseEntity<String> deletebyblogid(@PathVariable int blogid) {
        blogService.deletebyblogid(blogid);
        String msg = " blogs  Deleted Successfully";
        return new ResponseEntity<String>(msg, HttpStatus.OK);


    }
    @GetMapping(value = "/getblogbyid/{blogid}")
    public ResponseEntity<Blogs> getBlogById(@PathVariable int blogid) {
       Blogs blog = blogService.getBlogById(blogid);
        return new ResponseEntity<Blogs>(blog,HttpStatus.OK);
    }
    @PutMapping(value = "/editblogs/{blogid}")
    public ResponseEntity<?> editblogs(
            @PathVariable int blogid,
            @RequestParam(required = false) MultipartFile blogimage,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        try {
            Blogs existingBlog = blogService.getBlogById(blogid);
            if (existingBlog == null) {
                return ResponseEntity.notFound().build();
            }

            if (title != null && !title.isEmpty() && !existingBlog.getTitle().equals(title)) {
                BlogCategory blogCategory = existingBlog.getBlogCategory();
                Blogs blogWithSameTitleInCategory = blogService.getBlogByTitleAndCategory(title, blogCategory);

                if (blogWithSameTitleInCategory != null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title already exists in the same category");
                }
            }


            if (blogimage != null && !blogimage.isEmpty()) {
                try {
                    existingBlog.setBlogimage(blogimage.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (title != null && !title.isEmpty()) {
                existingBlog.setTitle(title);
            }
            if (content != null) {
                existingBlog.setContent(content);
            }
            Blogs updatedBlog = blogService.editblogs(blogid, existingBlog);
            return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}


