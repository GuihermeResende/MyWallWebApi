package com.example.MyWallWebApi.Service.Interface;

import com.example.MyWallWebApi.Domain.DTOs.PostDTO;
import com.example.MyWallWebApi.Domain.Models.Post;
import java.util.List;

public interface IPostService {

    List<PostDTO> listPosts();
    Post getPostById(Long postId);
    boolean createPost(PostDTO postDTO);
    PostDTO updatePost(PostDTO postDTO, Long id);
    boolean deletePost(Long postId);

}
