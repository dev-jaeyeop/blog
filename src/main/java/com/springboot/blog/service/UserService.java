package com.springboot.blog.service;

import com.springboot.blog.api.ApiResponse;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse> join(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("server error");
        }

        HttpStatus ok = HttpStatus.OK;
        ApiResponse success = new ApiResponse(ok, "Welcome to Blog", System.currentTimeMillis());

        return new ResponseEntity<>(success, ok);
    }

    @Transactional(readOnly = true) // 정합성 ()
    public ResponseEntity<ApiResponse> login(User user, HttpSession httpSession) {
        User principal = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())
                .orElseThrow(() -> new RuntimeException("Incorrect email or password."));

        httpSession.setAttribute("principal", principal);

        HttpStatus ok = HttpStatus.OK;
        ApiResponse success = new ApiResponse(ok, "success", System.currentTimeMillis());

        return new ResponseEntity<>(success, ok);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> validationEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isPresent()) {
            throw new RuntimeException("Email "+ email +" is invalid or already taken");
        }

        HttpStatus ok = HttpStatus.OK;
        ApiResponse success = new ApiResponse(ok, "Email " + email +" is available", System.currentTimeMillis());

        return new ResponseEntity<>(success, ok);
    }

}