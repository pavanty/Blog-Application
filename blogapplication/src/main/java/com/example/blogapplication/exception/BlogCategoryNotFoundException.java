package com.example.blogapplication.exception;

public class BlogCategoryNotFoundException extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    public BlogCategoryNotFoundException() {

    }

    public BlogCategoryNotFoundException(String s)
    {
        super(s);
    }

}