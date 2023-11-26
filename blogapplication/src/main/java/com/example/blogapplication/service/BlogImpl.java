package com.example.blogapplication.service;

import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.entity.Blogs;
import com.example.blogapplication.exception.BlogCategoryNotFoundException;
import com.example.blogapplication.exception.BlogNotFoundException;
import com.example.blogapplication.repository.BlogCategoryRepository;
import com.example.blogapplication.repository.BlogsRepository;
import com.example.blogapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BlogImpl implements  IBlogService {
    @Autowired
    BlogsRepository blogsRepository;

    @Autowired
    BlogCategoryRepository blogCategoryRepository;
    @Autowired
    UserRepository userRepository;
    public Blogs saveblog(int blogcategoryId, Blogs blogs) {
        Optional<BlogCategory> blogCategoryOptional = blogCategoryRepository.findById(blogcategoryId);
        if (blogCategoryOptional.isPresent()) {
            BlogCategory blogCategory = blogCategoryOptional.get();
            blogs.setBlogCategory(blogCategory);

            return blogsRepository.save(blogs);
        } else {
            throw new BlogCategoryNotFoundException("Blog Category not found");
        }
    }


    @Override
    public List<Blogs> showallBlogs() {
        return blogsRepository.findAll();
    }

    @Override
    public Blogs deletebyblogid(int blogid) {
        Optional<Blogs> blog1 = blogsRepository.findById(blogid);
        if (blog1.isPresent()) {
            blogsRepository.deleteById(blogid);
        } else {
            throw new BlogCategoryNotFoundException("BlogCategory is not existing");
        }
        return blog1.get();
    }

    @Override
    public Blogs getBlogById(int blogid) {
        return blogsRepository.findById(blogid)
                .orElseThrow(() -> new BlogNotFoundException("Blog category not found"));
    }

    @Override
    public Blogs updateisdeleted(Integer blogId) {
        Blogs b1 = blogsRepository.findById(blogId).get();
      b1.setIsdeleted(!b1.isIsdeleted());
      return  blogsRepository.save(b1);
    }

    @Override
    public boolean isTitleExists(String title) {
        return false;
    }


    @Override
    public Blogs editblogs(int blogid, Blogs existingCategory) {
        Optional<Blogs> optionalBlogs= blogsRepository.findById(blogid);
        if (optionalBlogs.isPresent()) {
            Blogs existingblogs = optionalBlogs.get();

            if (Objects.nonNull(existingCategory.getContent()) && !"".equalsIgnoreCase(existingCategory.getContent())) {
                existingCategory.setContent(existingCategory.getContent());
            }
            if (Objects.nonNull(existingCategory.getTitle()) && !"".equalsIgnoreCase(existingCategory.getTitle())) {
                existingCategory.setTitle(existingCategory.getTitle());
            }
            return blogsRepository.save(existingblogs);
        }
        return null;
    }

    @Override
    public boolean hasBlogsForCategory(int blogcategoryid) {
        List<Blogs> blogs = blogsRepository.findByBlogCategoryBlogcategoryid(blogcategoryid);
        return !blogs.isEmpty();
    }

    @Override
    public Blogs getBlogByTitleAndCategory(String title, BlogCategory blogCategory) {
        return blogsRepository.findByTitleAndBlogCategory(title, blogCategory);
    }

    public BlogCategory editblogcategory(int blogcategoryid, BlogCategory blogCategory) {
    Optional<BlogCategory> optionalCategory = blogCategoryRepository.findById(blogcategoryid);
        if (optionalCategory.isPresent()) {
        BlogCategory existingCategory = optionalCategory.get();

        if (Objects.nonNull(blogCategory.getDescription()) && !"".equalsIgnoreCase(blogCategory.getDescription())) {
            existingCategory.setDescription(blogCategory.getDescription());
        }

        if (Objects.nonNull(blogCategory.getTitle()) && !"".equalsIgnoreCase(blogCategory.getTitle())) {
            existingCategory.setTitle(blogCategory.getTitle());
        }



        return blogCategoryRepository.save(existingCategory);
    }
        return null;
}
}