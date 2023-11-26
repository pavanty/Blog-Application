package com.example.blogapplication.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class BlogAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer blogactionid;
    private boolean islike;

    private String  likedby;

    public BlogAction() {
    }

    public Integer getBlogactionid() {
        return blogactionid;
    }

    public void setBlogactionid(Integer blogactionid) {
        this.blogactionid = blogactionid;
    }

    public boolean isIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public String getLikedby() {
        return likedby;
    }

    public void setLikedby(String likedby) {
        this.likedby = likedby;
    }

    public User getRegister() {
        return register;
    }

    public void setRegister(User register) {
        this.register = register;
    }

    public Blogs getBlogs() {
        return blogs;
    }

    public void setBlogs(Blogs blogs) {
        this.blogs = blogs;
    }

    public BlogAction(Integer blogactionid, boolean islike, String likedby, User register, Blogs blogs) {
        this.blogactionid = blogactionid;
        this.islike = islike;
        this.likedby = likedby;
        this.register = register;
        this.blogs = blogs;
    }

    @ManyToOne
    @JoinColumn(name = "reg_id")
    @JsonProperty("blogactionregister")
    private User register;
    @ManyToOne
    @JoinColumn(name = "blogid")
    @JsonProperty("blogidfromaction")
    private Blogs blogs;

}
