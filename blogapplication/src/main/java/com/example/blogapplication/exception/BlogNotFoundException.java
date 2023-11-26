package com.example.blogapplication.exception;

public class BlogNotFoundException extends  RuntimeException {
    private static final long serialVersionUID = 1L;
    public BlogNotFoundException () {

    }
    public BlogNotFoundException(String s)
    {
        super(s);
    }
}
