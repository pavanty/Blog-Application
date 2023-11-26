package com.example.blogapplication.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentid;
    @Size(min = 4,max = 2000)
    @Column(length = 2000)

    private String commentcontent;
private Integer reg_id;

private String commentname;
    public Integer getCommentid() {
        return commentid;
    }





    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getModificationtime() {
        return modificationtime;
    }

    public void setModificationtime(String modificationtime) {
        this.modificationtime = modificationtime;
    }

    public String getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
    }

    public Blogs getBlogs() {
        return blogs;
    }

    public void setBlogs(Blogs blogs) {
        this.blogs = blogs;
    }

    private String createdby;
    private String modificationtime;

    private String creationtime;

    public Comments(Integer commentid, String commentcontent, Integer reg_id, String commentname, String createdby, String modificationtime, String creationtime, Blogs blogs) {
        this.commentid = commentid;
        this.commentcontent = commentcontent;
        this.reg_id = reg_id;
        this.commentname = commentname;
        this.createdby = createdby;
        this.modificationtime = modificationtime;
        this.creationtime = creationtime;
        this.blogs = blogs;
    }

    public String getCommentname() {
        return commentname;
    }

    public void setCommentname(String commentname) {
        this.commentname = commentname;
    }

    public Integer getReg_id() {
        return reg_id;
    }

    public void setReg_id(Integer reg_id) {
        this.reg_id = reg_id;
    }

    public Comments() {
    }

    @ManyToOne
    @JoinColumn(name = "blogid")
    private Blogs blogs;

}
