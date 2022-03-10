package com.example.MyWallWebApi.Service.Interface;

import com.example.MyWallWebApi.Domain.DTOs.MessageDTO;

import java.util.List;

public interface IMessageService {

    List<MessageDTO> listMessages();
    List<MessageDTO> listMessagesByUsers(Long userId, Long destinatarioId);            //mensagem da outra pessoa da conversa.
    boolean createMessage(MessageDTO messageDTO);
    boolean deleteMessage(Long messageId);
    boolean visualizarMessages(Long chatId);

}