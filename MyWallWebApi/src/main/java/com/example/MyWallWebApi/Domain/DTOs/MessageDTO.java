package com.example.MyWallWebApi.Domain.DTOs;

import com.example.MyWallWebApi.Domain.Models.Message;
import java.time.LocalDateTime;

public class MessageDTO {

    private Long id;
    private String conteudo;
    private LocalDateTime data;
    private long chatId;
    private long userId;
    private long destinatarioId;

    public long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static MessageDTO toDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.id = message.getId();
        messageDTO.conteudo = message.getConteudo();
        messageDTO.data = message.getData();
        messageDTO.chatId = message.getChat().getId();
        messageDTO.userId = message.getUser().getId();

        return messageDTO;
    }

}
