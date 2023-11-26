package com.example.blogapplication.exception;

public class BlogActionLikedException extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    public  BlogActionLikedException () {

    }

    public  BlogActionLikedException (String s)
    {
        super(s);
    }

}
