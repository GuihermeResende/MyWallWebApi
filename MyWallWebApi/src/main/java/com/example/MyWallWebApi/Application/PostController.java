package com.example.MyWallWebApi.Application;

import com.example.MyWallWebApi.Domain.DTOs.PostDTO;
import com.example.MyWallWebApi.Service.Implementation.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    PostService _postService;

    public PostController(PostService postService){
        _postService = postService;
    }

    @GetMapping("list-posts")
    public List<PostDTO> listPosts(){
        return _postService.listPosts();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getPostById(@PathVariable("id") Long id){
        try{
            PostDTO postDTO = PostDTO.toDTO(_postService.getPostById(id)); //Passar um DTO.
            return ResponseEntity.ok().body(postDTO);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/update-post/{id}")
    public ResponseEntity updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id) {
        try{
            PostDTO postUpdated = _postService.updatePost(postDTO,id);
            return ResponseEntity.ok(postUpdated);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create-post")
    public ResponseEntity createPost(@RequestBody PostDTO postDTO) {
        boolean ret = _postService.createPost(postDTO);

        return ResponseEntity.ok(ret);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable("id") Long id){
        _postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
