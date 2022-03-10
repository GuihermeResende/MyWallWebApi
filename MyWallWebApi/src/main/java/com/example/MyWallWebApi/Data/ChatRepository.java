package com.example.MyWallWebApi.Data;

import com.example.MyWallWebApi.Domain.Models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

     List<Chat> findChatsByUserId(Long userId); //listar chats do user
     Chat findChatByUser_IdAndUserDestinatario_Id(Long userId, Long destinatarioId);
     List<Chat> findChatsByUser_IdOrUserDestinatario_Id(Long userId, Long destinatarioId); //listar chats do user

}