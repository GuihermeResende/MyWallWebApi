package com.example.MyWallWebApi.Application;

import com.example.MyWallWebApi.Domain.DTOs.SignUpDTO;
import com.example.MyWallWebApi.Domain.Models.User;
import com.example.MyWallWebApi.Service.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity listUsers(){
        try{
            List<User> users = userService.listUsers();
            return ResponseEntity.ok(users);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id){
       try{
           User user = userService.getUserById(id);
           return ResponseEntity.ok().body(user);
       }catch (Exception ex) {
           return ResponseEntity.badRequest().body(ex.getMessage());
       }
    }

    @PostMapping("/update-user")
    public User updateUser(@RequestBody User user) {
        User userUpdated = userService.updateUser(user);
        return userUpdated;
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody SignUpDTO signUpDTO) {
        boolean ret = userService.SignUp(signUpDTO);

        return ResponseEntity.ok(ret);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
