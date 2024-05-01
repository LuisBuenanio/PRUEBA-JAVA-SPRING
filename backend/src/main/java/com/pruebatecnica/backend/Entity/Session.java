package com.pruebatecnica.backend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    private String token;

    private LocalDateTime date_init;
    private LocalDateTime date_close;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate_init() {
        return date_init;
    }

    public void setDate_init(LocalDateTime date_init) {
        this.date_init = date_init;
    }

    public LocalDateTime getDate_close() {
        return date_close;
    }

    public void setDate_close(LocalDateTime date_close) {
        this.date_close = date_close;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}