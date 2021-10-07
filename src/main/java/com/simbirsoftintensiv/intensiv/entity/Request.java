package com.simbirsoftintensiv.intensiv.entity;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
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
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Boolean isNull(){
        return !(this.getType() != null
                || this.getTitle() != null
                || this.getDate() != null
                || this.getAddress() != null
                || this.getClient() != null
                || this.getStatus() != null);
    }
}