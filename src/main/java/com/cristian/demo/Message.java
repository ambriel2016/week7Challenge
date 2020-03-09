package com.cristian.demo;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="title")
    @NonNull
    @Size(min=1, max=30)
    private String title;

    @Column(name="content")
    @NonNull
    @Size(min=1, max=300)
    private String content;

    @Column(name="postedDate")
    private String postedDate;

    @Column(name="postedBy")
    private String postedBy;

    @Column(name="photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Message() {
    }

    public Message(long id, String title, String content, String postedDate, String postedBy, String photo, User u){
        this.id = id;
        this.title = title;
        this.content = content;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
        this.photo = photo;
        this.user=u;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}