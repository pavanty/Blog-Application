package com.example.blogapplication.service;

import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.exception.BlogCategoryBlogpresentException;
import com.example.blogapplication.exception.BlogCategoryNotFoundException;
import com.example.blogapplication.repository.BlogCategoryRepository;
import com.example.blogapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class BlogCategoryImpl implements  IBlogCategoryService {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public BlogCategory saveblogcategory(int reg_id, BlogCategory blogCategory) {
        Optional<BlogCategory> blogCategory1 = blogCategoryRepository.findById(blogCategory.getBlogcategoryid());
        blogCategory.setUser(userRepository.getById(reg_id));
        return blogCategoryRepository.save(blogCategory);
    }

    @Override
    public List<BlogCategory> showallBlogCategory() {
        return blogCategoryRepository.findAll();
    }

    @Override
    public boolean isTitleExists(String title) {
        return blogCategoryRepository.existsByTitle(title);
    }

    @Override
    public BlogCategory deletebyblogcategoryid(int blogcategoryid) {
        Optional<BlogCategory> cart2 = blogCategoryRepository.findById(blogcategoryid);

        if (cart2.isPresent()) {
            if (blogCategoryRepository.ifBlogsPresent(blogcategoryid)) {
                throw new BlogCategoryBlogpresentException("This category cannot be deleted because it has associated blogs");
            } else {
                blogCategoryRepository.deleteById(blogcategoryid);
            }
        } else {
            throw new BlogCategoryNotFoundException("BlogCategory is not existing");
        }

        return cart2.get();
    }


//    @Override
//    public BlogCategory updateblogcategory(Integer blogcategoryid, BlogCategory blogCategory) {
//        BlogCategory b1 = blogCategoryRepository.findById(blogcategoryid).get();
//
//        if (Objects.nonNull(blogCategory.getDescription()) && !"".equalsIgnoreCase(blogCategory.getDescription())) {
//            b1.setDescription(blogCategory.getDescription());
//        }
//
//        if (Objects.nonNull(blogCategory.getTitle()) && !"".equalsIgnoreCase(blogCategory.getTitle())) {
//            b1.setTitle(blogCategory.getTitle());
//        }
//
//        if (Objects.nonNull(blogCategory.getCreatedBy()) && !"".equalsIgnoreCase(blogCategory.getCreatedBy())) {
//            b1.setCreatedBy(blogCategory.getCreatedBy());
//        }
//
//        if (Objects.nonNull(blogCategory.getModifiedby()) && !"".equalsIgnoreCase(blogCategory.getModifiedby())) {
//            b1.setModifiedby(blogCategory.getModifiedby());
//        }
//
//        if (Objects.nonNull(blogCategory.getModificationtime()) && !"".equalsIgnoreCase(blogCategory.getModificationtime())) {
//            b1.setModificationtime(blogCategory.getModificationtime());
//        }
//        return  blogCategoryRepository.save(b1);
//    }

    @Override
    public BlogCategory getBlogCategoryById(int blogcategoryid) {
        return blogCategoryRepository.findById(blogcategoryid)
                .orElseThrow(() -> new BlogCategoryNotFoundException("Blog category not found"));
    }
    @Override
    public BlogCategory getBlogCategoryByTitle(String title) {
        Optional<BlogCategory> blogCategoryOptional = blogCategoryRepository.findByTitle(title);

        if (blogCategoryOptional.isPresent()) {
            return blogCategoryOptional.get();
        } else {
            throw new BlogCategoryNotFoundException("Blog category not found with title: " + title);
        }
    }

    @Override
    public BlogCategory editblogcategory(int blogcategoryid, BlogCategory blogCategory) {
        Optional<BlogCategory> optionalCategory = blogCategoryRepository.findById(blogcategoryid);
        Optional<BlogCategory> samecategorytitle=blogCategoryRepository.findByTitle(blogCategory.getTitle());

        if (optionalCategory.isPresent()) {
            BlogCategory existingCategory = optionalCategory.get();

            if (Objects.nonNull(blogCategory.getDescription()) && !"".equalsIgnoreCase(blogCategory.getDescription())) {
                existingCategory.setDescription(blogCategory.getDescription());
            }
    if (Objects.nonNull(blogCategory.getTitle()) && !"".equalsIgnoreCase(blogCategory.getTitle()) && samecategorytitle.isPresent()) {
        existingCategory.setTitle(blogCategory.getTitle());

}


            return blogCategoryRepository.save(existingCategory);
        }
        return null;
    }




}
