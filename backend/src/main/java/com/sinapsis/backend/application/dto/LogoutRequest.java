package com.sinapsis.backend.application.dto;

public record LogoutRequest(String token) {

    public boolean isValid() {
        return token != null && !token.isBlank();
    }

}
