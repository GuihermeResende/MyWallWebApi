package com.example.MyWallWebApi.Application;

import com.example.MyWallWebApi.Domain.DTOs.MessageDTO;
import com.example.MyWallWebApi.Service.Implementation.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    MessageService _messageService;

    public MessageController(MessageService messageService) {
        _messageService = messageService;
    }

    @GetMapping()
    public ResponseEntity listMessages() {
        try{
            return ResponseEntity.ok(_messageService.listMessages());
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{idUser}/{idDestinatario}")
    public ResponseEntity listMessagesByUserId(@PathVariable("idUser") Long userId,@PathVariable("idDestinatario") Long destinatarioId) {
        try {
            return ResponseEntity.ok(_messageService.listMessagesByUsers(userId, destinatarioId));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create-message")
    public ResponseEntity createMessage(@RequestBody MessageDTO messageDTO) {
        try {
            return ResponseEntity.ok(_messageService.createMessage(messageDTO));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/visualizar-message/{chatId}")
    public ResponseEntity visualizarMessages(@PathVariable("chatId") Long chatId) {
        try {
            return ResponseEntity.ok(_messageService.visualizarMessages(chatId));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMessage(@PathVariable("id") Long messageId) {
        try {
            return ResponseEntity.ok(_messageService.deleteMessage(messageId));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}