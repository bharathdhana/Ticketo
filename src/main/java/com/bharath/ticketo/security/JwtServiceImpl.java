package com.bharath.ticketo.security;

import com.bharath.ticketo.exception.JwtValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigning() {
        byte[] encodedKey = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    @Override
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + Duration.ofDays(7).toMillis());
        return Jwts.builder()
               .claims(claims)
               .subject(email)
               .issuedAt(issuedAt)
               .expiration(expirationDate)
               .signWith(getSigning())
               .compact();
    }

    @Override
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigning())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isValidToken(String token, String username) {
        String extractedUsername = extractUserEmail(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claim = extractAllClaims(token);
            return claimsResolver.apply(claim);
        } catch (Exception e) {
            throw new JwtValidationException("Jwt Validation Exception");
        }
    }
}
