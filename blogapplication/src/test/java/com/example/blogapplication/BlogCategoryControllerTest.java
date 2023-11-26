package com.example.blogapplication;

import com.example.blogapplication.controller.BlogCategoryController;
import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.repository.BlogCategoryRepository;

import com.example.blogapplication.service.IBlogCategoryService;
import com.example.blogapplication.service.IuserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;






class BlogCategoryControllerTest {

        @Mock
        private BlogCategoryRepository blogCategoryRepository;
        @Mock
        IuserService iuserService;
        @Mock
        private IBlogCategoryService blogCategoryService;

        @InjectMocks
        private BlogCategoryController blogCategoryController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }



    @Test
    void testShowAllBlogCategory() {
        BlogCategory blogCategory = new BlogCategory();
        List<BlogCategory> blogCategories = Collections.singletonList(blogCategory);
        when(blogCategoryService.showallBlogCategory()).thenReturn(blogCategories);

        ResponseEntity<List<BlogCategory>> responseEntity = blogCategoryController.showallBlogCategory();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(blogCategories, responseEntity.getBody());

        verify(blogCategoryService, times(1)).showallBlogCategory();
    }

    @Test
    void testGetBlogCategoryById_ExistingCategory() {

        BlogCategory mockCategory = new BlogCategory();
        mockCategory.setBlogcategoryid(1);
        mockCategory.setTitle("Title");
        mockCategory.setDescription("Description");


        when(blogCategoryService.getBlogCategoryById(anyInt())).thenReturn(mockCategory);

        ResponseEntity<BlogCategory> responseEntity = blogCategoryController.getBlogCategoryById(1);

//comparing them with real one check
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockCategory, responseEntity.getBody());


        verify(blogCategoryService, times(1)).getBlogCategoryById(anyInt());
    }



}
