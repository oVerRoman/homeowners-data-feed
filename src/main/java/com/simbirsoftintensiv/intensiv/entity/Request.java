package com.simbirsoftintensiv.intensiv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "address")
    private Integer address;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status", nullable = false)
    private Integer status;

    public String getFileName() {
        return filename;
    }

    public void setFileName(String fileName) {
        this.filename = fileName;
    }

    @Column(name = "file")
    private String filename;

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    // @ManyToOne(optional = false)
    @Column(name = "client_id")
    private Integer client;



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
         this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
     //   this.type = 1;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public Boolean isNull(){
        return !(this.getType() != null
                || this.getTitle() != null
                || this.getDate() != null
                || this.getAddress() != null
                || this.getClient() != null
                || this.getStatus() != null);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", address=" + address +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", filename='" + filename + '\'' +
                ", client=" + client +
                '}';
    }
}