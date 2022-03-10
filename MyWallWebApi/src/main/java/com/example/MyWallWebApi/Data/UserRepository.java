package com.example.MyWallWebApi.Data;

import com.example.MyWallWebApi.Domain.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByNome(String nome);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findBySenha(String senha);

}
