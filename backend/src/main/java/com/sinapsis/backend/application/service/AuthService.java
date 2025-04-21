package com.sinapsis.backend.application.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinapsis.backend.application.dto.AuthRequest;
import com.sinapsis.backend.application.dto.AuthResponse;
import com.sinapsis.backend.domain.repository.UsuarioRepository;
import com.sinapsis.backend.infrastructure.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        var user = usuarioRepository.findByUsername(request.username()).orElseThrow();


        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        authManager.authenticate(auth);

        var jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }

    public void logout(String token) {
        jwtService.invalidateToken(token);
    }
}