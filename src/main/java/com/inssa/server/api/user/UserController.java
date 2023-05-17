package com.inssa.server.api.user;

import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserPasswordRequestDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.service.UserService;
import com.inssa.server.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "login", description = "사용자 로그인 API")
    @PostMapping("/login")
    public String login(@RequestBody UserRequestDto request) {
        return userService.login(request);
    }

    @Operation(summary = "register", description = "사용자 회원가입 API")
    @PostMapping("/register")
    public ApiResponse register(@RequestBody UserRegisterRequestDto request) {
        return userService.register(request);
    }

    @Operation(summary = "existsUserId", description = "아이디 중복 확인 API")
    @PostMapping("/exists/{userId}")
    public Boolean existsUserId(@PathVariable String userId) {
        return userService.existsUserId(userId);
    }

    @Operation(summary = "changeUserInfo", description = "회원 정보 변경 API")
    @PutMapping("/info")
    public ApiResponse changeUserInfo(@RequestBody UserChangeInfoRequestDto request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.changeUserInfo(request, userId);
    }

    @Operation(summary = "changePassword", description = "비밀번호 변경 API")
    @PutMapping("/password/change")
    public ApiResponse changePassword(@RequestBody UserPasswordRequestDto request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.changePassword(request, userId);
    }

    @Operation(summary = "checkPassword", description = "비밀번호 확인 API")
    @PostMapping("/password/check")
    public Boolean checkPassword(@RequestBody UserPasswordRequestDto request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.checkPassword(request, userId);
    }

    @Operation(summary = "leave", description = "회원 탈퇴 API")
    @PutMapping("/leave")
    public ApiResponse leave() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.leave(userId);
    }
}
