package com.xxavierr404.minitalk.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xxavierr404.minitalk.dto.UserDTO;
import com.xxavierr404.minitalk.services.UserDetailsServiceImpl;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@NonNullApi
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        } else {
            var jwt = authHeader.substring(7);
            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token");
            } else {
                try {
                    var userCredentialsDTO = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    var userDetails = userDetailsService.loadUserByUsername(userCredentialsDTO.getEmail());

                    if (!encoder.matches(userCredentialsDTO.getPassword(), userDetails.getPassword())) {
                        throw new JWTVerificationException("Invalid password");
                    }

                    var authToken = new UsernamePasswordAuthenticationToken(
                            new UserDTO(((UserDetailsImpl) userDetails).getUser()),
                            userCredentialsDTO,
                            userDetails.getAuthorities()
                    );

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }

                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }
    }
}
