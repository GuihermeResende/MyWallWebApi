package com.example.MyWallWebApi.Application;

import com.example.MyWallWebApi.Domain.DTOs.LikeDTO;
import com.example.MyWallWebApi.Service.Implementation.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    LikeService _likeService;

    public LikeController(LikeService likeService) {
        _likeService = likeService;
    }

    @GetMapping()
    public ResponseEntity listLikes() {
        try{
            return ResponseEntity.ok(_likeService.listLikes());
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity listMeusLikes(@PathVariable("id") Long userId) {
        try{
            return ResponseEntity.ok(_likeService.listMeusLikes(userId));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create-like")
    public ResponseEntity createLike(@RequestBody LikeDTO likeDTO) {
        try {
            return ResponseEntity.ok(_likeService.createLike(likeDTO));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create-unlike")
    public ResponseEntity createUnlike(@RequestBody LikeDTO likeDTO) {
        try {
            return ResponseEntity.ok(_likeService.createUnlike(likeDTO));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLike(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(_likeService.deleteLike(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
