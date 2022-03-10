package com.example.MyWallWebApi.Domain.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chat {

    public Chat(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destinatario_id")
    private User userDestinatario;

    private String statusChat;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserDestinatario() {
        return userDestinatario;
    }

    public void setUserDestinatario(User userDestinatario) {
        this.userDestinatario = userDestinatario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}