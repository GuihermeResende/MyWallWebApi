package com.example.MyWallWebApi.Service.Implementation;

import com.example.MyWallWebApi.Data.ChatRepository;
import com.example.MyWallWebApi.Data.MessageRepository;
import com.example.MyWallWebApi.Data.UserRepository;
import com.example.MyWallWebApi.Domain.DTOs.ChatDTO;
import com.example.MyWallWebApi.Domain.DTOs.MessageDTO;
import com.example.MyWallWebApi.Domain.Models.Chat;
import com.example.MyWallWebApi.Domain.Models.Message;
import com.example.MyWallWebApi.Domain.Models.User;
import com.example.MyWallWebApi.Service.Interface.IChatService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService {

    ChatRepository _chatRepository;
    UserRepository _userRepository;
    MessageRepository _messageRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository,  MessageRepository messageRepository) {
        _chatRepository = chatRepository;
        _userRepository = userRepository;
        _messageRepository = messageRepository;
    }

    @Override
    public List<ChatDTO> listMeusChats(Long userId) {
        List<Chat> chats = _chatRepository.findChatsByUserId(userId);

        if(chats.isEmpty()){
            throw new IllegalArgumentException("Não há chats deste usuário");
        }

        List<ChatDTO> chatsDTO = new ArrayList<>();

        for(Chat chat: chats) {
            chatsDTO.add(ChatDTO.toDTO(chat));
        }

        return  chatsDTO;
    }

    public Chat findChatByUsers(Long userId, Long destinatarioId) {
        Chat findChatByUser = _chatRepository.findChatByUser_IdAndUserDestinatario_Id(userId, destinatarioId); //ver se tem chat com usuário

        if (findChatByUser == null) {
            findChatByUser = _chatRepository.findChatByUser_IdAndUserDestinatario_Id(destinatarioId, userId);
        }
        return  findChatByUser;
    }

   @Override
    public ChatDTO createChat(ChatDTO chatDTO) { //chat vazio
        Optional <User> findUser =_userRepository.findById(chatDTO.getUserId());
        Optional <User> findDestinatario = _userRepository.findById(chatDTO.getDestinatarioId());

        if (!findUser.isPresent() || !findDestinatario.isPresent()){
            throw new IllegalArgumentException("Alguns dos usuários não existem");
        }

        Chat findChatByUser = _chatRepository.findChatByUser_IdAndUserDestinatario_Id(chatDTO.getUserId(), chatDTO.getDestinatarioId()); //ver se tem chat com usuário
        //Optional<Chat> findChatByDestinatario = _chatRepository.findChatByUserDestinatario_IdAndUser_Id(chatDTO.getDestinatarioId(), chatDTO.getUserId());

        if (findChatByUser != null) {
            throw new IllegalArgumentException("Você já possui um chat com este usuário");
        }

        findChatByUser = _chatRepository.findChatByUser_IdAndUserDestinatario_Id(chatDTO.getDestinatarioId(), chatDTO.getUserId()); //ver se tem chat com usuário

        if (findChatByUser != null) {
           throw new IllegalArgumentException("Você já possui um chat com este usuário");
        }

        Chat chat = new Chat();
        chat.setUser(findUser.get());
        chat.setUserDestinatario(findDestinatario.get());

        return ChatDTO.toDTO( _chatRepository.save(chat));
    }

    @Override
    public List<Chat> findChats(Long userId) {
        List<Chat> chats = _chatRepository.findChatsByUser_IdOrUserDestinatario_Id(userId, userId);

        return chats;
    }

    @Override
    public Chat createChatByMessage(MessageDTO messageDTO) { //antes crio a mensagem e chamo esse método...

        User user = new User();
        user.setId(messageDTO.getUserId());

        User userDestinatario = new User();
        userDestinatario.setId(messageDTO.getDestinatarioId());

        Chat chat = new Chat();
        chat.setUser(user);
        chat.setUserDestinatario(userDestinatario);

        return _chatRepository.save(chat);
    }

    @Override
    public Boolean deleteChat(Long chatId, Long userId, Long destinatarioId) {
        List<Message> findMessages = _messageRepository.findMessagesByUser_IdAndUserDestinatario_Id(userId,destinatarioId);

        if (!findMessages.isEmpty()) {
            for (Message messages : findMessages) {
                findMessages.remove(messages);
                _messageRepository.delete(messages);
            }
        }

        Chat findChat = _chatRepository.findChatByUser_IdAndUserDestinatario_Id(userId, destinatarioId);

        if (findChat == null) {
            throw new IllegalArgumentException("Este chat não existe");
        }

        _chatRepository.delete(findChat);

        return true;
    }

}