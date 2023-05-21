package com.xxavierr404.minitalk.controllers;

import com.xxavierr404.minitalk.dto.UserCredentialsDTO;
import com.xxavierr404.minitalk.dto.UserDTO;
import com.xxavierr404.minitalk.dto.UserRegisterDTO;
import com.xxavierr404.minitalk.exceptions.EmailTakenException;
import com.xxavierr404.minitalk.security.JwtUtil;
import com.xxavierr404.minitalk.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    private ResponseEntity<?> doRegister(UserRegisterDTO dto) {
        try {
            userDetailsService.doRegister(dto);
        } catch (EmailTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(jwtUtil.generateToken(UserCredentialsDTO.fromRegisterDTO(dto)));
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody UserCredentialsDTO dto) {
        return ResponseEntity.ok(jwtUtil.generateToken(dto));
    }

    @GetMapping("/getUserInfo")
    private ResponseEntity<UserDTO> getUserInfo() {
        return ResponseEntity.ok(new UserDTO(userDetailsService.getUser().orElseThrow()));
    }

    @GetMapping("/checkToken")
    private ResponseEntity<Boolean> checkToken() {
        return ResponseEntity.ok(userDetailsService.getUser().isPresent());
    }
}
