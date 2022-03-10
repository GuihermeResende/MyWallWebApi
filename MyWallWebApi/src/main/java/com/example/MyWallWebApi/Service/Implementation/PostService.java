package com.example.MyWallWebApi.Service.Implementation;

import com.example.MyWallWebApi.Data.DislikeRepository;
import com.example.MyWallWebApi.Data.LikeRepository;
import com.example.MyWallWebApi.Data.PostRepository;
import com.example.MyWallWebApi.Domain.DTOs.PostDTO;
import com.example.MyWallWebApi.Domain.Models.Dislike;
import com.example.MyWallWebApi.Domain.Models.Like;
import com.example.MyWallWebApi.Domain.Models.Post;
import com.example.MyWallWebApi.Domain.Models.User;
import com.example.MyWallWebApi.Service.Interface.IPostService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    PostRepository _postRepository;
    LikeRepository _likeRepository;
    DislikeRepository _dislikeRepository;

    public PostService(PostRepository postRepository, LikeRepository likeRepository, DislikeRepository dislikeRepository) {
        _postRepository = postRepository;
        _likeRepository = likeRepository;
        _dislikeRepository = dislikeRepository;
    }

    public List<PostDTO> listPosts() {
        List<Post> posts = _postRepository.findAll();
        List<PostDTO> postsDTO = new ArrayList<>();

        for (Post post : posts) {
            postsDTO.add(PostDTO.toDTO(post));
        }

        return postsDTO;
    }

    public Post getPostById(Long postId) {
        Optional<Post> post = _postRepository.findById(postId);

        if (post.isPresent()) {
            return post.get();
        }
        else throw new IllegalArgumentException("O post não existe");
    }

    public boolean createPost(PostDTO postDTO) {

        User user = new User();
        user.setId(postDTO.getUserDTO().getId());

        Post post = new Post();
        post.setUser(user);
        post.setTitulo(postDTO.getTitulo());
        post.setConteudo(postDTO.getConteudo());
        post.setData(LocalDateTime.now());

        _postRepository.save(post);

        return true;
    }

    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post findPost = getPostById(id);

        findPost.setTitulo(postDTO.getTitulo());
        findPost.setConteudo(postDTO.getConteudo());

        return PostDTO.toDTO( _postRepository.save(findPost));
    }

    public boolean deletePost(Long postId) {
        Post findPost = getPostById(postId);

        if (findPost == null) {
            throw new IllegalArgumentException("O post não existe");
        }

        List<Like> likes = _likeRepository.findLikesByPostId(postId);

        for (Like like: likes) {
            _likeRepository.delete(like);
        }

        List<Dislike> dislikes = _dislikeRepository.findDislikesByPostId(postId);

        for (Dislike dislike: dislikes) {
            _dislikeRepository.delete(dislike);
        }

        _postRepository.delete(findPost);

        return true;
    }

}
