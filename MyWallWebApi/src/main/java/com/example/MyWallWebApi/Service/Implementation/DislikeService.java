package com.example.MyWallWebApi.Service.Implementation;

import com.example.MyWallWebApi.Data.DislikeRepository;
import com.example.MyWallWebApi.Data.LikeRepository;
import com.example.MyWallWebApi.Data.PostRepository;
import com.example.MyWallWebApi.Data.UserRepository;
import com.example.MyWallWebApi.Domain.DTOs.DislikeDTO;
import com.example.MyWallWebApi.Domain.Models.Dislike;
import com.example.MyWallWebApi.Domain.Models.Like;
import com.example.MyWallWebApi.Domain.Models.Post;
import com.example.MyWallWebApi.Domain.Models.User;
import com.example.MyWallWebApi.Service.Interface.IDislikeService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DislikeService implements IDislikeService {

    DislikeRepository _dislikeRepository;
    PostRepository _postRepository;
    LikeRepository _likeRepository;
    UserRepository _userRepository;

    public DislikeService (DislikeRepository dislikeRepository, PostRepository postRepository, LikeRepository likeRepository, UserRepository userRepository) {
        _dislikeRepository = dislikeRepository;
        _postRepository = postRepository;
        _likeRepository = likeRepository;
        _userRepository = userRepository;
    }

    @Override
    public List<DislikeDTO> listDislikes() {
        List<Dislike> dislikes =  _dislikeRepository.findAll();
        List<DislikeDTO> dislikesDTO = new ArrayList<>();

        for (Dislike dislike: dislikes) {
            dislikesDTO.add(DislikeDTO.toDTO(dislike));
        }

        return dislikesDTO;
    }

    @Override
    public List<DislikeDTO> listMeusDislikes(Long userId) {
       List<Dislike> dislikes = _dislikeRepository.findDisikesByUserId(userId);
       List<DislikeDTO> dislikeDTO = new ArrayList<>();

       for (Dislike dislike: dislikes) {
            dislikeDTO.add(DislikeDTO.toDTO(dislike));
       }

       return dislikeDTO;
    }

    @Override
    public DislikeDTO createDislike(DislikeDTO dislikeDTO) {
         Dislike findDislike = _dislikeRepository.findByUserIdAndPostId(dislikeDTO.getUserId(), dislikeDTO.getPostId());

         if (findDislike != null) {
             throw new IllegalArgumentException("Você já deu dislike neste post");
         }

         Post post = _postRepository.findById(dislikeDTO.getPostId()).get();

         Like findLike = _likeRepository.findByUserIdAndPostId(dislikeDTO.getUserId(), dislikeDTO.getPostId());

         if (findLike != null) {
             post.setContadorLikes(post.getContadorLikes() - 1);
             post.setContadorDislikes(post.getContadorDislikes() + 1);
         } else if (findLike == null) {
             post.setContadorDislikes(post.getContadorDislikes() + 1);
         }

         _postRepository.save(post);

         User user = _userRepository.findById(dislikeDTO.getUserId()).orElse(null);

         if (user == null) {
             throw new IllegalArgumentException("O usuário não existe");
         }

         Dislike dislike = new Dislike();
         dislike.setUser(user);
         dislike.setPost(post);

        return DislikeDTO.toDTO(_dislikeRepository.save(dislike));
    }

    public boolean createUndislike(DislikeDTO dislikeDTO) {
        Dislike findDislike = _dislikeRepository.findByUserIdAndPostId(dislikeDTO.getUserId(), dislikeDTO.getPostId());

        if (findDislike == null) {
            throw new IllegalArgumentException("o dislike não existe");
        }

        User user = new User();
        user.setId(dislikeDTO.getUserId());

        Post post = _postRepository.findById(dislikeDTO.getPostId()).get();
        post.setContadorDislikes(post.getContadorDislikes() - 1);
        _postRepository.save(post);

        findDislike.setId(dislikeDTO.getId());

        _dislikeRepository.delete(findDislike);

        return true;
    }

    @Override
    public boolean deleteDislike(Long dislikeId) {
        Dislike findDislike = _dislikeRepository.findById(dislikeId).get();
        Post findPost = _postRepository.findById(findDislike.getPost().getId()).get();

        if (findDislike == null) {
            throw new IllegalArgumentException("O dislike não existe");
        }

        findPost.setContadorDislikes(findPost.getContadorDislikes() - 1);

        _dislikeRepository.delete(findDislike);

        return true;
    }

}