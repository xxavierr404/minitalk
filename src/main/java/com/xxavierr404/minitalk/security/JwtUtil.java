package com.xxavierr404.minitalk.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xxavierr404.minitalk.dto.UserCredentialsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(UserCredentialsDTO credentialsDTO) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", credentialsDTO.getEmail())
                .withClaim("password", credentialsDTO.getPassword())
                .withIssuedAt(new Date())
                .withIssuer("Minitalk")
                .sign(Algorithm.HMAC256(secret));
    }

    public UserCredentialsDTO validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        var verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Minitalk")
                .build();
        var jwt = verifier.verify(token);
        var creds = new UserCredentialsDTO();
        creds.setEmail(jwt.getClaim("email").asString());
        creds.setPassword(jwt.getClaim("password").asString());
        return creds;
    }
}
