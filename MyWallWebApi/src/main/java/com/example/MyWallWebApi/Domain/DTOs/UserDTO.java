package com.example.MyWallWebApi.Domain.DTOs;

import com.example.MyWallWebApi.Domain.Models.User;

public class UserDTO {

    private Long id;
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.id = user.getId();
        userDTO.nome = user.getNome();

        return userDTO;
    }

}
