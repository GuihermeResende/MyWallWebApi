package com.example.MyWallWebApi.Data;

import com.example.MyWallWebApi.Domain.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

        List<Message> findMessagesByUser_IdAndUserDestinatario_Id(Long userId, Long destinatarioId);
        List<Message> findMessagesByChat_IdAndStatusMensagemNot(Long chatId, boolean status);
        List<Message> findMessagesByUser_IdOrUserDestinatario_Id(Long userId, Long destinatarioId);

}