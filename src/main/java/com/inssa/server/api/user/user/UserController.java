package com.inssa.server.api.user.user;

import com.inssa.server.api.user.social.dto.SocialUserRegisterRequestDto;
import com.inssa.server.api.user.social.model.SocialType;
import com.inssa.server.api.user.social.service.OauthService;
import com.inssa.server.api.user.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.user.dto.UserPasswordRequestDto;
import com.inssa.server.api.user.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.user.dto.UserRequestDto;
import com.inssa.server.api.user.user.model.AuthUser;
import com.inssa.server.api.user.user.model.User;
import com.inssa.server.api.user.user.service.UserService;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "user", description = "회원 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final OauthService oauthService;
    private final HttpServletResponse response;

    @Operation(summary = "사용자 로그인 API", tags = "user")
    @PostMapping("/login")
    public InssaApiResponse<Map<String, Object>> login(@RequestBody UserRequestDto request) {
        String token = userService.login(request);

        return InssaApiResponse.success(Map.of("token", token));
    }

    @Operation(summary = "소셜 로그인 Redirect URL API", tags = "user")
    @GetMapping(value = "/auth/social/{socialLoginType}")
    public void getRedirectUrl(@PathVariable(name = "socialLoginType") SocialType socialType) {
        String redirectUrl = oauthService.getRedirectUrl(socialType);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "소셜 로그인 callback API", tags = "user")
    @GetMapping(value = "/auth/social/{socialLoginType}/callback")
    public InssaApiResponse<Map<String, Object>> oAuthLogin(@PathVariable(name = "socialLoginType") SocialType socialType, @RequestParam(name = "code") String code) {
        return InssaApiResponse.success(oauthService.oAuthLogin(socialType, code));
    }

    @Operation(summary = "사용자 회원가입 API", tags = "user")
    @PostMapping("/register")
    public InssaApiResponse<Map<String, Object>> register(@RequestBody UserRegisterRequestDto request) {
        Long userNo = userService.register(request);
        return InssaApiResponse.success(Map.of("userNo", userNo));
    }

    @Operation(summary = "사용자 소셜 회원가입 API", tags = "user")
    @PostMapping("/social/register")
    public InssaApiResponse<Map<String, Object>> socialRegister(@RequestBody SocialUserRegisterRequestDto request) {
        String token = userService.socialRegister(request);
        return InssaApiResponse.success(Map.of("token", token));
    }

    @Operation(summary = "이메일 중복 확인 API", tags = "user")
    @PostMapping("/user/exists/{email}")
    public InssaApiResponse<Boolean> existsEmail(@PathVariable String email) {
        return InssaApiResponse.success(userService.existsEmail(email));
    }

    @Operation(summary = "닉네임 중복 확인 API", tags = "user")
    @PostMapping("/user/exists/nickname/{nickname}")
    public InssaApiResponse<Boolean> existsNickname(@PathVariable String nickname) {
        return InssaApiResponse.success(userService.existsNickname(nickname));
    }

    @Operation(summary = "회원 정보 변경 API", tags = "user")
    @PreAuthorizeLogInUser
    @PutMapping("/user/info")
    public InssaApiResponse<User> changeUserInfo(@RequestBody UserChangeInfoRequestDto request, @AuthenticationPrincipal AuthUser user) {
        return InssaApiResponse.success(userService.changeUserInfo(request, user.getUserNo()));
    }

    @Operation(summary = "비밀번호 변경 API", tags = "user")
    @PreAuthorizeLogInUser
    @PutMapping("/user/password/change")
    public InssaApiResponse<Map<String, Object>> changePassword(@RequestBody UserPasswordRequestDto request, @AuthenticationPrincipal AuthUser user) {
        Long userNo = userService.changePassword(request, user.getUserNo());
        return InssaApiResponse.success(Map.of("userNo", userNo));
    }

    @Operation(summary = "비밀번호 확인 API", tags = "user")
    @PreAuthorizeLogInUser
    @PostMapping("/user/password/check")
    public InssaApiResponse<Boolean> checkPassword(@RequestBody UserPasswordRequestDto request, @AuthenticationPrincipal AuthUser user) {
        return InssaApiResponse.success(userService.checkPassword(request, user.getUserNo()));
    }

    @Operation(summary = "회원 탈퇴 API", tags = "user")
    @PreAuthorizeLogInUser
    @PutMapping("/leave")
    public InssaApiResponse<Map<String, Object>> leave(@AuthenticationPrincipal AuthUser user) {
        userService.leave(user.getUserNo());

        return InssaApiResponse.success(ResponseCode.DELETED, Map.of("userNo", user.getUserNo()));
    }
}
