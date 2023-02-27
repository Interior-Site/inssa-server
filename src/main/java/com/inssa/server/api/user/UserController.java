package com.inssa.server.api.user;

import com.inssa.server.api.user.dto.UserLoginRequestDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.service.UserService;
import com.inssa.server.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "user", description = "사용자 API")
@Slf4j
public class UserController {

    private final UserService userService;

    @Tag(name = "user")
    @Operation(summary = "login", description = "사용자 로그인 API")
    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequestDto request) {
        return userService.login(request);
    }

    @Tag(name = "user")
    @Operation(summary = "register", description = "사용자 회원가입 API")
    @PostMapping("/register")
    public ApiResponse register(@RequestBody UserRegisterRequestDto request) {
        return userService.register(request);
    }
    
}
