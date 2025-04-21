package com.sinapsis.backend.application.dto;

import org.apache.commons.lang3.StringUtils;

public record AuthRequest (String username, String password){

    public boolean isValid() {
        return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
    }
}
