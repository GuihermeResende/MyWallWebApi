package com.example.MyWallWebApi.Service.Implementation;

import com.example.MyWallWebApi.Data.*;
import com.example.MyWallWebApi.Domain.DTOs.SignUpDTO;
import com.example.MyWallWebApi.Domain.Models.*;
import com.example.MyWallWebApi.Service.Interface.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {

    private UserRepository _userRepository;
    private ChatRepository _chatRepository;
    private MessageRepository _messageRepository;
    private PostRepository _postRepository;
    private LikeRepository _likeRepository;
    private DislikeRepository _dislikeRepository;

    public UserService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, PostRepository postRepository,LikeRepository likeRepository, DislikeRepository dislikeRepository) {
        _userRepository = userRepository;
        _chatRepository = chatRepository;
        _messageRepository = messageRepository;
        _postRepository = postRepository;
        _likeRepository = likeRepository;
        _dislikeRepository = dislikeRepository;
    }

    public List<User> listUsers() {
        List<User> listUsers = _userRepository.findAll();
        return listUsers;
    }

    public User getUserById(Long userID) {
        User user = _userRepository.findById(userID).get();

        if (user == null) {
            throw new IllegalArgumentException("Usuário não existe");
        }
        return user;
    }

    public User updateUser(User user) {
        User findUser = _userRepository.findById(user.getId()).get();

        if (findUser == null) {
            throw new IllegalArgumentException("Usuário não existe");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        findUser.setNome(user.getNome());
        findUser.setEmail(user.getEmail());
        findUser.setSenha(encoder.encode(user.getSenha()));
        findUser.setUsername(user.getUsername());

        return _userRepository.save(findUser);
    }

    public Boolean deleteUser(Long userId) {
        User findUser = _userRepository.findById(userId).orElse(null);

        if (findUser == null) {
            throw new IllegalArgumentException("Usuário não existe");
        }

        List<Chat> findChats = _chatRepository.findChatsByUser_IdOrUserDestinatario_Id(userId, userId);
        for (Chat chat : findChats) {
            for (Message message : _messageRepository.findMessagesByUser_IdOrUserDestinatario_Id(userId, userId)) {
                _messageRepository.delete(message);
            }

            _chatRepository.delete(chat);
        }

        for (Like like : _likeRepository.findLikesByUserId(userId)) {
            _likeRepository.delete(like);
        }

        for (Dislike dislike: _dislikeRepository.findDisikesByUserId(userId)) {
            _dislikeRepository.delete(dislike);
        }

        List<Post> findPosts = _postRepository.findPostsByUser_Id(userId);
        for (Post post : findPosts) {
            for (Like like : _likeRepository.findLikesByPostId(post.getId())) {
                _likeRepository.delete(like);
            }

            for (Dislike dislike: _dislikeRepository.findDislikesByPostId(post.getId())) {
                _dislikeRepository.delete(dislike);
            }

            _postRepository.delete(post);
        }

        _userRepository.delete(findUser);

        return true;
    }

    public Boolean SignUp(SignUpDTO signUpDTO) {
        if (!signUpDTO.getPassword().equals(signUpDTO.getPasswordConfirm())) {
            throw new IllegalArgumentException("a senha confirmada é inválida");
        }

        Optional<User> userExists = _userRepository.findByNome(signUpDTO.getUsername());
        if (userExists.isPresent()) {
            throw new IllegalArgumentException("O usuáro já existe");
        }

        Optional<User> emailExists = _userRepository.findByEmail(signUpDTO.getEmail());
        if (emailExists.isPresent()) {
            throw new IllegalArgumentException("O email já existe");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "ROLE_USER"));

        User user = new User();
        user.setNome(signUpDTO.getNome());
        user.setRoles(roles);
        user.setEmail(signUpDTO.getEmail());
        user.setSenha(encoder.encode(signUpDTO.getPassword()));
        user.setUsername(signUpDTO.getUsername());

        User result = _userRepository.save(user);
       return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return _userRepository.findByUsername(username).get();
    }

}

