package com.aidar.tuneflow.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.expirationInMs}")
    private long jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return JWT.create()
                .withIssuer(jwtIssuer)
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .withArrayClaim("roles", userPrincipal.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .toArray(String[]::new))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(jwtIssuer)
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }
}