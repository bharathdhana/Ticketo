package com.bharath.ticketo.security;

import java.util.Date;

public interface JwtService {
    String generateToken(String email);
    String extractUserEmail(String token);
    Date extractExpiration(String token);
    boolean isValidToken(String token, String username);
    boolean isTokenExpired(String token);
}
