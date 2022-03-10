package com.example.MyWallWebApi.Service.Implementation;

import com.example.MyWallWebApi.Data.ChatRepository;
import com.example.MyWallWebApi.Data.MessageRepository;
import com.example.MyWallWebApi.Data.UserRepository;
import com.example.MyWallWebApi.Domain.DTOs.ChatDTO;
import com.example.MyWallWebApi.Domain.DTOs.MessageDTO;
import com.example.MyWallWebApi.Domain.Models.*;
import com.example.MyWallWebApi.Service.Interface.IMessageService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {

    MessageRepository _messageRepository;
    ChatRepository _chatRepository;
    ChatService _chatService;
    UserRepository _userRepository;

    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, ChatService chatService, UserRepository userRepository) {
        _messageRepository = messageRepository;
        _chatRepository = chatRepository;
        _chatService = chatService;
        _userRepository = userRepository;
    }

    @Override
    public List<MessageDTO> listMessages() {
        List<Message> messages = _messageRepository.findAll();
        List<MessageDTO> messagesDTO = new ArrayList<>();

        for(Message message: messages) {
            messagesDTO.add(MessageDTO.toDTO(message));
        }

        return messagesDTO;
    }

    @Override
    public List<MessageDTO> listMessagesByUsers(Long userId, Long destinatarioId) {
       Chat findChat = _chatRepository.findChatByUser_IdAndUserDestinatario_Id(userId, destinatarioId);

        if(findChat != null) {
            throw new IllegalArgumentException("Não existe chat com esses usuários");
        }

        List<Message> findMessages = _messageRepository.findMessagesByUser_IdAndUserDestinatario_Id(userId, destinatarioId);

        if (findMessages == null) {
            throw new IllegalArgumentException("Não existe mensagens desses usuários");
        }

        List<MessageDTO> messagesDTO = new ArrayList<>();

        for (Message message : findMessages) {
            messagesDTO.add(MessageDTO.toDTO(message));
        }

        return messagesDTO;
    }


    @Override
    public boolean createMessage(MessageDTO messageDTO) {

        User findUser = _userRepository.findById(messageDTO.getUserId()).orElse(null);

        if (findUser == null) {
            throw new IllegalArgumentException("O usuário não existe");
        }

        User findUserDestinatario = _userRepository.findById(messageDTO.getDestinatarioId()).orElse(null);

        if (findUserDestinatario == null) {
            throw new IllegalArgumentException("O usuário destinatário não existe");
        }

        Chat chat = new Chat();

        Chat findChat;
        findChat = _chatService.findChatByUsers(messageDTO.getUserId(), messageDTO.getDestinatarioId());
        if (findChat == null) {
            findChat.setUser(findUser);
            findChat.setUserDestinatario(findUserDestinatario);
            findChat = _chatService.createChatByMessage(messageDTO);
        }

        Message message = new Message();

        User user = new User();
        user.setId(messageDTO.getUserId());
        message.setUser(user);

        User userDestinatario = new User();
        userDestinatario.setId(messageDTO.getDestinatarioId());
        message.setUserDestinatario(userDestinatario);

        message.setChat(findChat);
        message.setConteudo(messageDTO.getConteudo());
        message.setData(LocalDateTime.now());

        _messageRepository.save(message);

        return true;
    }

    @Override
    public boolean visualizarMessages(Long chatId) {
        List<Message> findMessages = _messageRepository.findMessagesByChat_IdAndStatusMensagemNot(chatId, true);

        if (findMessages.isEmpty()) {
            throw new IllegalArgumentException("Esta mensagem não existe");
        }

        for (Message message : findMessages) {
             message.setStatusMensagem(true);
        }

        _messageRepository.saveAll(findMessages);

        return true;
    }

    @Override
    public boolean deleteMessage(Long messageId) {
        Message findMessage = _messageRepository.findById(messageId).get();

        if(findMessage == null) {
            throw new IllegalArgumentException("Esta mensagem não existe");
        }

        _messageRepository.delete(findMessage);
        return true;
    }

}
