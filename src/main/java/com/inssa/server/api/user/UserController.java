package com.inssa.server.api.user;

import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserPasswordRequestDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.api.user.service.UserService;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.common.response.ApiResponse;
import com.inssa.server.common.response.InssaApiResponse;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "user", description = "회원 API")
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Tag(name = "user")
    @Operation(summary = "login", description = "사용자 로그인 API")
    @PostMapping("/login")
    public InssaApiResponse login(@RequestBody UserRequestDto request) {
        String token = userService.login(request);

        return InssaApiResponse.ok(Map.of("token", token));
    }

    @Tag(name = "user")
    @Operation(summary = "register", description = "사용자 회원가입 API")
    @PostMapping("/register")
    public InssaApiResponse register(@RequestBody UserRegisterRequestDto request) {
        Long userNo = userService.register(request);
        return InssaApiResponse.ok(Map.of("userNo", userNo));
    }

    @Tag(name = "user")
    @Operation(summary = "existsUserId", description = "이메일 중복 확인 API")
    @PostMapping("/exists/{email}")
    public InssaApiResponse existsEmail(@PathVariable String email) {
        return InssaApiResponse.ok(userService.existsEmail(email));
    }

    @Tag(name = "user")
    @Operation(summary = "changeUserInfo", description = "회원 정보 변경 API")
    @PutMapping("/info")
    public InssaApiResponse changeUserInfo(@RequestBody UserChangeInfoRequestDto request, @AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }

        return InssaApiResponse.ok(userService.changeUserInfo(request, Long.parseLong(user.getUsername())));
    }

    @Tag(name = "user")
    @Operation(summary = "changePassword", description = "비밀번호 변경 API")
    @PutMapping("/password/change")
    public InssaApiResponse changePassword(@RequestBody UserPasswordRequestDto request, @AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }

        Long userNo = userService.changePassword(request, Long.parseLong(user.getUsername()));
        return InssaApiResponse.ok(Map.of("userNo", userNo));
    }

    @Tag(name = "user")
    @Operation(summary = "checkPassword", description = "비밀번호 확인 API")
    @PostMapping("/password/check")
    public InssaApiResponse checkPassword(@RequestBody UserPasswordRequestDto request, @AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }

        return InssaApiResponse.ok(userService.checkPassword(request, Long.parseLong(user.getUsername())));
    }

    @Tag(name = "user")
    @Operation(summary = "leave", description = "회원 탈퇴 API")
    @PutMapping("/leave")
    public InssaApiResponse leave(@AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }
        userService.leave(Long.parseLong(user.getUsername()));

        return InssaApiResponse.ok();
    }
}
