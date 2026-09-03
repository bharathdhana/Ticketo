package com.bharath.ticketo.service.impl;

import com.bharath.ticketo.dto.auth.AuthResponse;
import com.bharath.ticketo.dto.auth.LoginRequest;
import com.bharath.ticketo.dto.auth.RegisterRequest;
import com.bharath.ticketo.exception.ResourceNotFoundException;
import com.bharath.ticketo.model.User;
import com.bharath.ticketo.model.enums.UserRole;
import com.bharath.ticketo.repository.UserRepository;
import com.bharath.ticketo.security.JwtService;
import com.bharath.ticketo.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Email or Password"));

        if(!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid Email or Password");
        }

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    @Transactional
    public String register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public String upgradeRole(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(UserRole.ADMIN);
        userRepository.save(user);
        return "User Upgraded to Admin";
    }
}
