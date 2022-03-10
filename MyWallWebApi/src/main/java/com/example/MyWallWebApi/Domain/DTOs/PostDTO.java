package com.example.MyWallWebApi.Domain.DTOs;

import com.example.MyWallWebApi.Domain.Models.Post;
import java.time.LocalDateTime;

public class PostDTO {

    private Long id;
    private String titulo;
    private String conteudo;
    private LocalDateTime data;
    private UserDTO userDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public static PostDTO toDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.id = post.getId();
        postDTO.conteudo = post.getConteudo();
        postDTO.data = post.getData();
        postDTO.titulo = post.getTitulo();
        postDTO.userDTO = UserDTO.toDTO(post.getUser());

        return postDTO;
    }

}