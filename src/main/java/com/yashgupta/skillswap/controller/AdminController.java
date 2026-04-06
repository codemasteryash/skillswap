package com.yashgupta.skillswap.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.repository.UserRepository;
import com.yashgupta.skillswap.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;
    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam Long requesterId) {
        userService.requireAdmin(requesterId);
        return userRepository.findAll();
    }
}
