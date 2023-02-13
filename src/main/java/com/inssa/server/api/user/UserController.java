package com.inssa.server.api.user;

import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(@RequestBody UserRequestDto request) {
        return userService.login(request);
    }
    
}
