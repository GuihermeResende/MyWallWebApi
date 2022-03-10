package com.example.MyWallWebApi.Security.JWT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JwtLoginInput {

    private String username;
    private String senha;

}

