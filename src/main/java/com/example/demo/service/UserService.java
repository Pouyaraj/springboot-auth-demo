package com.example.demo.service;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;


    public UserService(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    public User saveUser(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            String msg = messageSource.getMessage(
                    "error.email.duplicate",
                    new Object[]{ user.getEmail() },
                    LocaleContextHolder.getLocale()
            );
            throw new EmailAlreadyExistException(msg);
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

}