package com.example.MyWallWebApi.Service.Interface;

import com.example.MyWallWebApi.Domain.DTOs.SignUpDTO;
import com.example.MyWallWebApi.Domain.Models.User;

import java.util.List;

public interface IUserService {

    List<User> listUsers();
    User getUserById(Long userID);
    User updateUser(User user);
    Boolean deleteUser(Long userId);
    Boolean SignUp(SignUpDTO signUpDTO);

}
