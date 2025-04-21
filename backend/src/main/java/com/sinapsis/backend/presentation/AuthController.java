package com.sinapsis.backend.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinapsis.backend.application.dto.AuthRequest;
import com.sinapsis.backend.application.dto.AuthResponse;
import com.sinapsis.backend.application.dto.LogoutRequest;
import com.sinapsis.backend.application.service.AuthService;
import com.sinapsis.backend.presentation.exception.ErrorResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        if (!request.isValid()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            AuthResponse response = authService.login(request);            
            return ResponseEntity.ok(response);    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("INVALID_CREDENTIALS", "Invalid credentials"));
        }
        
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {

        if (!logoutRequest.isValid()) {
            return ResponseEntity.badRequest().build();
        }

        authService.logout(logoutRequest.token());
        return ResponseEntity.noContent().build();
    }

}
