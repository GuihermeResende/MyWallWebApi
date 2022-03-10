package com.example.MyWallWebApi.Application;

import com.example.MyWallWebApi.Domain.DTOs.DislikeDTO;
import com.example.MyWallWebApi.Service.Implementation.DislikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dislike")
public class DislikeController {

    DislikeService _dislikeService;

    public DislikeController(DislikeService dislikeService) {
        _dislikeService = dislikeService;
    }

    @GetMapping()
    public ResponseEntity listDisLikes() {
        try{
            return ResponseEntity.ok(_dislikeService.listDislikes());
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity listMeusDislikes(@PathVariable("id") Long userId) {
        try{
            return ResponseEntity.ok(_dislikeService.listMeusDislikes(userId));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create-dislike")
    public ResponseEntity createDislike(@RequestBody DislikeDTO dislikeDTO) {
        try {
            return ResponseEntity.ok(_dislikeService.createDislike(dislikeDTO));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create-undislike")
    public ResponseEntity createUndislike(@RequestBody DislikeDTO dislikeDTO) {
        try {
            return ResponseEntity.ok(_dislikeService.createUndislike(dislikeDTO));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDislike(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(_dislikeService.deleteDislike(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
