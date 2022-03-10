package com.example.MyWallWebApi.Domain.DTOs;

import com.example.MyWallWebApi.Domain.Models.Chat;
import com.example.MyWallWebApi.Domain.Models.Message;
import java.util.List;

public class ChatDTO {

    private Long id;
    private List<Message> messages;
    private String chatStatus;
    private Long userId;
    private Long destinatarioId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(String chatStatus) {
        this.chatStatus = chatStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public static ChatDTO toDTO(Chat chat) {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.id = chat.getId();
        chatDTO.userId = chat.getUser().getId();
        chatDTO.destinatarioId = chat.getUserDestinatario().getId();

        return chatDTO;
    }

}
