package com.bharath.ticketo.service;

import com.bharath.ticketo.dto.auth.AuthResponse;
import com.bharath.ticketo.dto.auth.LoginRequest;
import com.bharath.ticketo.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    String register(RegisterRequest request);
    String upgradeRole(Long id);
}
