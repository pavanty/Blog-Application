package com.example.blogapplication.controller;

import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.exception.BlogCategoryBlogpresentException;
import com.example.blogapplication.repository.BlogCategoryRepository;
import com.example.blogapplication.service.IBlogCategoryService;
import com.example.blogapplication.service.IBlogService;
import com.example.blogapplication.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/blogcategory")

@CrossOrigin(value = "*")
public class BlogCategoryController {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Autowired
    private IBlogCategoryService blogCategoryService;

    @Autowired
    private IuserService iuserService;

    @Autowired
    private IBlogService blogService;
    @PostMapping(value = "/blogcategory/{reg_id}")
    public ResponseEntity<?> saveBlogCategory(
            @PathVariable int reg_id,
            @RequestParam Integer blogcategoryid,
            @RequestParam MultipartFile blogcategoryimage,
            @RequestParam String title,
            @RequestParam String CreatedBy,
            @RequestParam String Modifiedby,
            @RequestParam String modificationtime,
            @RequestParam String creationtime,
            @RequestParam String description
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        UserDto userDto = (UserDto) authentication.getPrincipal();
        if (!userDto.getReg_id().equals(reg_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have access to this resource");
        }

        if (title == null || title.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title is Required");
        }
        if (blogcategoryimage == null || blogcategoryimage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blog Category Image is required");
        }

        if (description == null || description.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description Required");
        }

        if (blogCategoryService.isTitleExists(title)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title already exists");
        }


        BlogCategory blogCategory = new BlogCategory();
        try {
            blogCategory.setBlogcategoryimage(blogcategoryimage.getBytes()); // Store image as byte array
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        blogCategory.setBlogcategoryid(blogcategoryid);
        blogCategory.setTitle(title);
        blogCategory.setDescription(description);
        blogCategory.setCreatedBy(CreatedBy);
        blogCategory.setModifiedby(Modifiedby);
        blogCategory.setModificationtime(modificationtime);
        blogCategory.setCreationtime(creationtime);

        BlogCategory savedCategory = blogCategoryService.saveblogcategory(reg_id, blogCategory);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }


    @GetMapping(value = "/showallblogcategory")
    public ResponseEntity<List<BlogCategory>> showallBlogCategory() {
        List<BlogCategory> allBlogCategory = blogCategoryService.showallBlogCategory();
        return new ResponseEntity<List<BlogCategory>>(allBlogCategory, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletebyblogcategoryid/{blogcategoryid}")
    public ResponseEntity<String> deletebyblogcategoryid(@PathVariable int blogcategoryid) {


        blogCategoryService.deletebyblogcategoryid(blogcategoryid);
        String msg = " blogcategory  Deleted Successfully";
        return new ResponseEntity<String>(msg, HttpStatus.OK);

    }



    @GetMapping(value = "/getblogcategorybyid/{blogcategoryid}")
    public ResponseEntity<BlogCategory> getBlogCategoryById(@PathVariable int blogcategoryid) {
        BlogCategory blogCategory = blogCategoryService.getBlogCategoryById(blogcategoryid);
        return new ResponseEntity<BlogCategory>(blogCategory, HttpStatus.OK);
    }
    @GetMapping(value = "/getblogcategorybytitle/{title}")
    public ResponseEntity<BlogCategory> getBlogCategoryByTitle(@PathVariable String title) {
        BlogCategory blogCategorytitle = blogCategoryService.getBlogCategoryByTitle(title);
        return new ResponseEntity<>(blogCategorytitle, HttpStatus.OK);
    }



    @PutMapping(value = "/editblogcategory/{blogcategoryid}")
    public ResponseEntity<?> editblogcategory(
            @PathVariable int blogcategoryid,
            @RequestParam(required = false) MultipartFile blogcategoryimage,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description
    ) {
        try {
            BlogCategory existingCategory = blogCategoryService.getBlogCategoryById(blogcategoryid);
            if (existingCategory == null) {
                return ResponseEntity.notFound().build();
            }
            if (title != null && !title.isEmpty() && !existingCategory.getTitle().equals(title) && blogCategoryService.isTitleExists(title)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title already exists");
            }
            if (blogcategoryimage != null && !blogcategoryimage.isEmpty()) {
                try {
                    existingCategory.setBlogcategoryimage(blogcategoryimage.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (title != null && !title.isEmpty()) {
                existingCategory.setTitle(title);
            }
            if (description != null) {
                existingCategory.setDescription(description);
            }

            BlogCategory updatedCategory = blogCategoryService.editblogcategory(blogcategoryid, existingCategory);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
