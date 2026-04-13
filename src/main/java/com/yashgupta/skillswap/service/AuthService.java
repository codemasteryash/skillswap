package com.yashgupta.skillswap.service;

import com.yashgupta.skillswap.dto.AuthResponse;
import com.yashgupta.skillswap.dto.LoginRequest;
import com.yashgupta.skillswap.dto.RegisterRequest;
import com.yashgupta.skillswap.entity.Role;
import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.repository.UserRepository;
import com.yashgupta.skillswap.security.JwtService;
import com.yashgupta.skillswap.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        UserDetailsImpl principal = new UserDetailsImpl(userRepository.findByEmail(user.getEmail()).orElseThrow());
        return buildResponse(jwtService.generateToken(principal), principal);
    }
    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        return buildResponse(jwtService.generateToken(principal), principal);
    }
    private AuthResponse buildResponse(String token, UserDetailsImpl principal) {
        User u = principal.getUser();
        return AuthResponse.builder()
                .token(token)
                .userId(u.getUserId())
                .email(u.getEmail())
                .username(u.getUsername())
                .role(u.getRole())
                .build();
    }
}
