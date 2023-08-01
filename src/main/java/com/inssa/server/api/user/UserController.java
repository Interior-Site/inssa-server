package com.inssa.server.api.user;

import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserPasswordRequestDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.api.user.model.User;
import com.inssa.server.api.user.service.UserService;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@Tag(name = "user", description = "회원 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 로그인 API", tags = "user")
    @PostMapping("/login")
    public InssaApiResponse<Map<String, Object>> login(@RequestBody UserRequestDto request) {
        String token = userService.login(request);

        return InssaApiResponse.ok(Map.of("token", token));
    }

    @Operation(summary = "사용자 회원가입 API", tags = "user")
    @PostMapping("/register")
    public InssaApiResponse<Map<String, Object>> register(@RequestBody UserRegisterRequestDto request) {
        Long userNo = userService.register(request);
        return InssaApiResponse.ok(Map.of("userNo", userNo));
    }

    @Operation(summary = "이메일 중복 확인 API", tags = "user")
    @PostMapping("/user/exists/{email}")
    public InssaApiResponse<Boolean> existsEmail(@PathVariable String email) {
        return InssaApiResponse.ok(userService.existsEmail(email));
    }

    @Operation(summary = "회원 정보 변경 API", tags = "user")
    @PutMapping("/user/info")
    public InssaApiResponse<User> changeUserInfo(@RequestBody UserChangeInfoRequestDto request, @AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }

        return InssaApiResponse.ok(userService.changeUserInfo(request, Long.parseLong(user.getUsername())));
    }

    @Operation(summary = "비밀번호 변경 API", tags = "user")
    @PutMapping("/user/password/change")
    public InssaApiResponse<Map<String, Object>> changePassword(@RequestBody UserPasswordRequestDto request, @AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }

        Long userNo = userService.changePassword(request, Long.parseLong(user.getUsername()));
        return InssaApiResponse.ok(Map.of("userNo", userNo));
    }

    @Operation(summary = "비밀번호 확인 API", tags = "user")
    @PostMapping("/user/password/check")
    public InssaApiResponse<Boolean> checkPassword(@RequestBody UserPasswordRequestDto request, @AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }

        return InssaApiResponse.ok(userService.checkPassword(request, Long.parseLong(user.getUsername())));
    }

    @Operation(summary = "회원 탈퇴 API", tags = "user")
    @PutMapping("/leave")
    public InssaApiResponse<Map<String, Object>> leave(@AuthenticationPrincipal AuthUser user) {
        if(user == null) {
            throw new InssaException("로그인 후 이용 가능합니다");
        }
        userService.leave(Long.parseLong(user.getUsername()));

        return InssaApiResponse.ok(ResponseCode.DELETED, Map.of("userNo", user.getUsername()));
    }
}
