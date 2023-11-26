package com.example.blogapplication;

import com.example.blogapplication.controller.CommentsController;
import com.example.blogapplication.entity.Comments;
import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.exception.CommentNotFoundException;
import com.example.blogapplication.repository.CommentRepository;
import com.example.blogapplication.service.ICommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentsTests {
    @Mock
    private ICommentService commentService;

    @InjectMocks
    private CommentsController commentsController;
    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveComment_ValidInput() {

        Comments comments = new Comments();
        comments.setReg_id(1);

        UserDto userDto = new UserDto();
        userDto.setReg_id(1);


        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, null);


        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(commentService.savecomment(anyInt(), any(Comments.class))).thenReturn(comments);


        ResponseEntity<?> responseEntity = commentsController.savecomment(1, comments);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Comments);
        Comments returnedComments = (Comments) responseEntity.getBody();
        assertEquals(comments, returnedComments);


        verify(commentService, times(1)).savecomment(anyInt(), any(Comments.class));
    }

    @Test
    void testGetAllComments() {
        Comments comment = new Comments();
        List<Comments> commentsList = Collections.singletonList(comment);
        when(commentService.getallcomment()).thenReturn(commentsList);

        ResponseEntity<List<Comments>> responseEntity = commentsController.getallcomment();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentsList, responseEntity.getBody());

        verify(commentService, times(1)).getallcomment();
    }

    @Test
    void testGetAllCommentsByBlogId() {
        int blogId = 1;
        Comments comment = new Comments();
        List<Comments> commentsList = Collections.singletonList(comment);
        when(commentService.getAllCommentsByBlogId(eq(blogId))).thenReturn(commentsList);

        ResponseEntity<List<Comments>> responseEntity = commentsController.getAllCommentsByBlogId(blogId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentsList, responseEntity.getBody());

        verify(commentService, times(1)).getAllCommentsByBlogId(eq(blogId));
    }





    @Test
    void testGetCommentById_ExistingComment() {
        int commentId = 1;


        Comments comment = new Comments();
        comment.setCommentid(commentId);

        when(commentService.getCommentById(commentId)).thenReturn(comment);


        ResponseEntity<Comments> responseEntity = commentsController.getCommentById(commentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(comment, responseEntity.getBody());


        verify(commentService, times(1)).getCommentById(commentId);
    }


}