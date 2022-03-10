package com.example.MyWallWebApi.Data;

import com.example.MyWallWebApi.Domain.Models.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DislikeRepository extends JpaRepository<Dislike,Long> {

    List<Dislike> findDisikesByUserId(Long userId);
    Dislike findByUserIdAndPostId(Long userId, Long postId);
    List<Dislike> findDislikesByPostId(Long postId);

}