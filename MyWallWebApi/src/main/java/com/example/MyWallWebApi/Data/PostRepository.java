package com.example.MyWallWebApi.Data;

import com.example.MyWallWebApi.Domain.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitulo(String titulo);
    Optional<Post> findByData(String data);
    Optional<Post> findByUser(String nome);
    List<Post> findPostsByUser_Id(Long userId);

}
