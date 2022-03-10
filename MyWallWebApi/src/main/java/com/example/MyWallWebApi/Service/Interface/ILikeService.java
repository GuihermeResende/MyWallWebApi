package com.example.MyWallWebApi.Service.Interface;

import com.example.MyWallWebApi.Domain.DTOs.LikeDTO;
import java.util.List;

public interface ILikeService {

    List<LikeDTO> listLikes();
    List<LikeDTO> listMeusLikes(Long userId);
    LikeDTO createLike(LikeDTO likeDTO);
    boolean deleteLike(Long likeId);

}
