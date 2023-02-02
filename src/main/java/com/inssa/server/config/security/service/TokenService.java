package com.inssa.server.config.security.service;

import com.inssa.server.config.security.model.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenService {

    public AuthUser findAuthUserByToken(String token) {
        log.debug("Token Service => token : {}", token);

        return null;
    }
}
