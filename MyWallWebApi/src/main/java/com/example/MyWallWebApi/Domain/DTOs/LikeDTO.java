package com.example.MyWallWebApi.Domain.DTOs;

import com.example.MyWallWebApi.Domain.Models.Like;

public class LikeDTO {

    private Long id;
    private Long userId;
    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public static LikeDTO toDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.id = like.getId();
        likeDTO.userId = like.getUser().getId();
        likeDTO.postId = like.getPost().getId();

        return likeDTO;
    }

}
