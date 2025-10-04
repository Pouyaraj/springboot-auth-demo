package com.example.demo.service;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistException;
import com.example.demo.exception.EmptyPasswordException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, MessageSource messageSource, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
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

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            String msg = messageSource.getMessage(
                    "error.password.empty",
                    null,
                    LocaleContextHolder.getLocale()
            );
            throw new EmptyPasswordException(msg);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // New method for user authentication
    public User authenticateUser(User user){
        User authenticator = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(authenticator !=null && passwordEncoder.matches(user.getPassword(), authenticator.getPassword())){
            return authenticator;
        } else{
            return null;
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }


}