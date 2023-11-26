package com.example.blogapplication.entity;

import com.example.blogapplication.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
public class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer blogcategoryid;
    @Column(length = 2000)
    private String description;
    @Column(unique = true)
    private String title;
    private String CreatedBy;
    private String Modifiedby;
    private String modificationtime;
    private String creationtime;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] blogcategoryimage;

    public BlogCategory() {
    }

    public Integer getBlogcategoryid() {
        return blogcategoryid;
    }

    public void setBlogcategoryid(Integer blogcategoryid) {
        this.blogcategoryid = blogcategoryid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getModifiedby() {
        return Modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        Modifiedby = modifiedby;
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

    public byte[] getBlogcategoryimage() {
        return blogcategoryimage;
    }

    public void setBlogcategoryimage(byte[] blogcategoryimage) {
        this.blogcategoryimage = blogcategoryimage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlogCategory(Integer blogcategoryid, String description, String title, String createdBy, String modifiedby, String modificationtime, String creationtime, byte[] blogcategoryimage, User user) {
        this.blogcategoryid = blogcategoryid;
        this.description = description;
        this.title = title;
        CreatedBy = createdBy;
        Modifiedby = modifiedby;
        this.modificationtime = modificationtime;
        this.creationtime = creationtime;
        this.blogcategoryimage = blogcategoryimage;
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "register_id",referencedColumnName = "reg_id")
    @JsonProperty("register")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User user;

}
