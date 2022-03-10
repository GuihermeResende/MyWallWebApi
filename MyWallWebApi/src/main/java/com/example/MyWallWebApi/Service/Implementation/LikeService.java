package com.example.MyWallWebApi.Service.Implementation;

import com.example.MyWallWebApi.Data.DislikeRepository;
import com.example.MyWallWebApi.Data.LikeRepository;
import com.example.MyWallWebApi.Data.PostRepository;
import com.example.MyWallWebApi.Domain.DTOs.LikeDTO;
import com.example.MyWallWebApi.Domain.Models.Dislike;
import com.example.MyWallWebApi.Domain.Models.Like;
import com.example.MyWallWebApi.Domain.Models.Post;
import com.example.MyWallWebApi.Domain.Models.User;
import com.example.MyWallWebApi.Service.Interface.ILikeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService implements ILikeService {

    LikeRepository _likeRepository;
    DislikeRepository _dislikeRepository;
    PostRepository _postRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, DislikeRepository dislikeRepository) {
        _likeRepository = likeRepository;
        _postRepository = postRepository;
        _dislikeRepository = dislikeRepository;
    }

    @Override
    public List<LikeDTO> listLikes() {
        List<Like> likes = _likeRepository.findAll();
        List<LikeDTO> likesDTO = new ArrayList<>();

        for (Like like : likes) {
            likesDTO.add(LikeDTO.toDTO(like));
        }

        return likesDTO;
    }

    @Override
    public List<LikeDTO> listMeusLikes(Long userId) {
        List<Like> likes = _likeRepository.findLikesByUserId(userId);
        List<LikeDTO> likesDTO = new ArrayList<>();

        for (Like like : likes) {
            likesDTO.add(LikeDTO.toDTO(like));
        }

        return likesDTO;
    }

    @Override
    public LikeDTO createLike(LikeDTO likeDTO) {
        Like findLike = _likeRepository.findByUserIdAndPostId(likeDTO.getUserId(), likeDTO.getPostId());

        if (findLike != null) {
            throw new IllegalArgumentException("Você já deu like neste post");
        }

        Post post = _postRepository.findById(likeDTO.getPostId()).get();
        Dislike findDislike = _dislikeRepository.findByUserIdAndPostId(likeDTO.getUserId(), likeDTO.getPostId());

        if (findDislike != null) {
            post.setContadorDislikes(post.getContadorDislikes() - 1);
            post.setContadorLikes(post.getContadorLikes() + 1);
        } else if (findDislike == null) {
            post.setContadorLikes(post.getContadorLikes() + 1);
    }

        _postRepository.save(post);

        User user = new User();
        user.setId(likeDTO.getUserId());

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        return LikeDTO.toDTO(_likeRepository.save(like));
    }

    public boolean createUnlike(LikeDTO likeDTO) {
        Like findLike = _likeRepository.findByUserIdAndPostId(likeDTO.getUserId(), likeDTO.getPostId());

        if (findLike == null) {
            throw new IllegalArgumentException("Este like não existe");
        }

        Post post = _postRepository.findById(likeDTO.getPostId()).get();
        post.setContadorLikes(post.getContadorLikes() - 1);

        User user = new User();
        user.setId(likeDTO.getUserId());

        findLike.setUser(user);
        findLike.setPost(post);
        findLike.setId(likeDTO.getId());

        _postRepository.save(post);

        _likeRepository.delete(findLike);

        return true;
    }

    @Override
    public boolean deleteLike(Long likeId) {
        Like findLike = _likeRepository.findById(likeId).get();
        Post findPost = _postRepository.findById(findLike.getPost().getId()).get();

        if (findLike == null) {
            throw new IllegalArgumentException("O like não existe");
        }

        findPost.setContadorLikes(findPost.getContadorLikes() - 1);
        _likeRepository.delete(findLike);

        return true;
    }

}
