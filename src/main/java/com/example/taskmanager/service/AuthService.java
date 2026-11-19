
package com.example.taskmanager.service;

import com.example.taskmanager.dto.AuthResponseDTO;
import com.example.taskmanager.dto.LoginRequestDTO;
import com.example.taskmanager.dto.RegisterRequestDTO;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.security.JwtService;

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
    public String register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Email is already registered");
        }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());

        // Hash the password before saving
        user.setPassword(
                passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return "User registered successfully";
    }


public AuthResponseDTO login(LoginRequestDTO request) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

    String email = authentication.getName();

    String token = jwtService.generateToken(email);

    return new AuthResponseDTO(token, "Bearer");
}

}
