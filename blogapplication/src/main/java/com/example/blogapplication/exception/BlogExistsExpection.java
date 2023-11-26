package com.example.blogapplication.exception;

public class BlogExistsExpection  extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    public BlogExistsExpection() {

    }

    public BlogExistsExpection(String s)
    {
        super(s);
    }

}