package com.example.MyWallWebApi.Service.Interface;

import com.example.MyWallWebApi.Domain.DTOs.DislikeDTO;
import com.example.MyWallWebApi.Domain.Models.Dislike;

import java.util.List;

public interface IDislikeService {

    List<DislikeDTO> listDislikes();
    List<DislikeDTO> listMeusDislikes(Long userId);
    DislikeDTO createDislike(DislikeDTO dislikeDTO);
    boolean deleteDislike(Long dislikeId);

}
