package com.example.MyWallWebApi.Service.Interface;

import com.example.MyWallWebApi.Domain.DTOs.ChatDTO;
import com.example.MyWallWebApi.Domain.DTOs.MessageDTO;
import com.example.MyWallWebApi.Domain.Models.Chat;

import java.util.List;

public interface IChatService {

    List<ChatDTO> listMeusChats(Long userId);
    ChatDTO createChat(ChatDTO chatDTO);
    Chat createChatByMessage(MessageDTO messageDTO);
    Chat findChatByUsers(Long userId, Long destinatarioId);
    List<Chat> findChats(Long userId);
    Boolean deleteChat(Long chatId, Long userId, Long destinatarioId);

}
