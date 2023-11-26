package com.example.blogapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "title", "blogcategory_id" }))
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogid;

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL)
    private List<Comments> comments;


    private String title;
    @Column(length = 500000)
    private String Content;
    @Lob
    private byte[] blogimage;

    public User getRegister() {
        return register;
    }

    public void setRegister(User register) {
        this.register = register;
    }



    private String date;
    @ManyToOne
    @JoinColumn(name = "reg_id")
    @JsonProperty("blogregister")
    private User register;

    private boolean isdeleted;
    @ManyToOne(optional = false)
    @JoinColumn(name = "blogcategory_id",referencedColumnName = "blogcategoryid")
    private BlogCategory blogCategory;
    public Integer getBlogid() {
        return blogid;
    }

    public void setBlogid(Integer blogid) {
        this.blogid = blogid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public byte[] getBlogimage() {
        return blogimage;
    }

    public void setBlogimage(byte[] blogimage) {
        this.blogimage = blogimage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Blogs() {
    }


    public Blogs(Integer blogid, List<Comments> comments, String title, String content, byte[] blogimage, String date, User register, boolean isdeleted, BlogCategory blogCategory) {
        this.blogid = blogid;
        this.comments = comments;
        this.title = title;
        Content = content;
        this.blogimage = blogimage;
        this.date = date;
        this.register = register;
        this.isdeleted = isdeleted;
        this.blogCategory = blogCategory;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public BlogCategory getBlogCategory() {
        return blogCategory;
    }


    public void setBlogCategory(BlogCategory blogCategory) {
        this.blogCategory = blogCategory;
    }


}
