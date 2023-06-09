package com.xxavierr404.minitalk.services;

import com.xxavierr404.minitalk.dto.UserDTO;
import com.xxavierr404.minitalk.dto.UserRegisterDTO;
import com.xxavierr404.minitalk.exceptions.EmailTakenException;
import com.xxavierr404.minitalk.model.User;
import com.xxavierr404.minitalk.repositories.UserRepository;
import com.xxavierr404.minitalk.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.getByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No such user");
        }
        return new UserDetailsImpl(user.get());
    }

    public void doRegister(UserRegisterDTO dto) {
        var user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setBirthdate(dto.getBirthdate());
        user.setCity(dto.getCity());
        user.setUniversity(dto.getUniversity());

        try {
            loadUserByUsername(dto.getEmail());
        } catch (UsernameNotFoundException e) {
            userRepository.save(user);
            return;
        }

        throw new EmailTakenException("Email already taken");
    }

    public Optional<User> getUser() {
        return userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId());
    }
}
