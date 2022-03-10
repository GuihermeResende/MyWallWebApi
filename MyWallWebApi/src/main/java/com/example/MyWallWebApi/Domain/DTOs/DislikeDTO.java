package com.example.MyWallWebApi.Domain.DTOs;

import com.example.MyWallWebApi.Domain.Models.Dislike;

public class DislikeDTO {

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

    public static DislikeDTO toDTO(Dislike dislike) {
        DislikeDTO dislikeDTO = new DislikeDTO();
        dislikeDTO.id = dislike.getId();
        dislikeDTO.postId = dislike.getPost().getId();
        dislikeDTO.userId = dislike.getUser().getId();

        return dislikeDTO;
    }

}
