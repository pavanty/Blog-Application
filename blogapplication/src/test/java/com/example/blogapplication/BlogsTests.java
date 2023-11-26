package com.example.blogapplication;

import com.example.blogapplication.controller.BlogsController;
import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.BlogCategory;
import com.example.blogapplication.entity.Blogs;
import com.example.blogapplication.entity.User;
import com.example.blogapplication.repository.BlogCategoryRepository;
import com.example.blogapplication.repository.BlogsRepository;
import com.example.blogapplication.repository.UserRepository;
import com.example.blogapplication.service.IBlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class BlogsTests {

    @Mock
    private IBlogService blogService;

    @Mock
    private BlogCategoryRepository blogCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BlogsRepository blogsRepository;

    @InjectMocks
    private BlogsController blogsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBlog_ValidInput() {

        MockMultipartFile imageFile = new MockMultipartFile(
                "blogimage", "test.png", "image/png", "Image Content".getBytes()
        );


        when(blogCategoryRepository.findById(anyInt())).thenReturn(Optional.of(new BlogCategory()));
        when(blogsRepository.findByTitleAndBlogCategory(anyString(), any(BlogCategory.class))).thenReturn(null);
        when(blogService.saveblog(anyInt(), any(Blogs.class))).thenReturn(new Blogs());


        UserDto userDto = new UserDto();
        userDto.setReg_id(1);


        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, null);


        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        User user = new User();
        user.setReg_id(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<String> responseEntity = blogsController.saveBlog(
                1, 1, "Title", 1, "Content", imageFile, "Date", false
        );


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Blog saved successfully", responseEntity.getBody());


        verify(blogService, times(1)).saveblog(anyInt(), any(Blogs.class));
    }


    @Test
    void testUpdateIsDeleted_ValidInput() {
        when(blogService.updateisdeleted(anyInt())).thenReturn(new Blogs());

        ResponseEntity<Blogs> responseEntity = blogsController.updateisdeleted(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(blogService, times(1)).updateisdeleted(anyInt());
    }

    @Test
    void testShowAllBlogs() {
        Blogs blog = new Blogs();
        List<Blogs> blogsList = Collections.singletonList(blog);
        when(blogService.showallBlogs()).thenReturn(blogsList);

        ResponseEntity<List<Blogs>> responseEntity = blogsController.showallBlogs();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(blogsList, responseEntity.getBody());

        verify(blogService, times(1)).showallBlogs();
    }
}
