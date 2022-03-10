package com.example.MyWallWebApi.Data;

import com.example.MyWallWebApi.Domain.Models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findLikesByUserId(Long userId);
    Like findByUserIdAndPostId(Long userId, Long postId);
    List<Like> findLikesByPostId(Long postId);

}
