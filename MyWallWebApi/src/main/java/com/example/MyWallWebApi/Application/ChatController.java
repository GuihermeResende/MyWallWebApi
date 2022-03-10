package com.example.MyWallWebApi.Application;

import com.example.MyWallWebApi.Domain.DTOs.ChatDTO;
import com.example.MyWallWebApi.Service.Implementation.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    ChatService _chatService;

    public ChatController(ChatService chatService) {
        _chatService = chatService;
    }

    @GetMapping("/{id}")
    public ResponseEntity listMeusChats(@PathVariable("id") Long userId) {
        try{
            return ResponseEntity.ok(_chatService.listMeusChats(userId));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create-chat")
    public ResponseEntity createChat(@RequestBody ChatDTO chatDTO) {
        try {
            return ResponseEntity.ok(_chatService.createChat(chatDTO));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{userId}/{destinatarioId}")
    public ResponseEntity findChatByUsers(@PathVariable("userId") Long userId, @PathVariable("destinatarioId") Long destinatarioId) {
        try {
            return ResponseEntity.ok(_chatService.findChatByUsers(userId, destinatarioId));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/find-chats")
    public ResponseEntity findChats(@RequestBody Long userId) {
        try {
            return ResponseEntity.ok(_chatService.findChats(userId));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}/{userId}/{destinatarioId}")
    public ResponseEntity deleteChat(@PathVariable("id") Long chatId,@PathVariable("userId") Long userId, @PathVariable("destinatarioId") Long destinatarioId) {
        try {
            return ResponseEntity.ok(_chatService.deleteChat(chatId, userId, destinatarioId));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
