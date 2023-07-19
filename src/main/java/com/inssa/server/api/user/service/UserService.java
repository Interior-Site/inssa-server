package com.inssa.server.api.user.service;

import com.inssa.server.api.user.data.UserRepository;
import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserPasswordRequestDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.api.user.model.EnumRole;
import com.inssa.server.api.user.model.User;
import com.inssa.server.common.response.ApiResponse;
import com.inssa.server.common.response.ResponseMessage;
import com.inssa.server.common.code.StatusCode;
import com.inssa.server.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("UserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = findByUserId(userId);

        // 로그인 유저의 권한 목록 주입
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleKey()));

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), authorities);
    }

    public String login(UserRequestDto request) {
        User user = findByUserId(request.getUserId());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword()));
    }

    @Transactional
    public ApiResponse register(UserRegisterRequestDto request) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        User registerUser = userRepository.save(User.builder()
                                                    .userId(request.getUserId())
                                                    .password(passwordEncoder.encode(request.getPassword().trim()))
                                                    .email(request.getEmail())
                                                    .nickname(request.getNickname())
                                                    .phone(request.getPhone())
                                                    .role(EnumRole.USER)
                                                    .build());

        if(registerUser != null) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userNo", registerUser.getNo());
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public Boolean existsUserId(String userId) {
        if(userId == null || userId.equals("")) {
            throw new IllegalArgumentException("아이디를 입력해 주세요");
        }

        return userRepository.existsByUserId(userId);
    }

    @Transactional
    public ApiResponse changeUserInfo(UserChangeInfoRequestDto request, String userId) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        User user = findByUserId(userId);
        user.changeInfo(request.getEmail(), request.getNickname(), request.getPhone());

        statusCode = StatusCode.SUCCESS;
        message = ResponseMessage.SUCCESS;
        response.putData("userNo", user.getNo());

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    @Transactional
    public ApiResponse changePassword(UserPasswordRequestDto request, String userId) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        User user = findByUserId(userId);
        String pw = request.getPassword();
        if (pw != null && !pw.trim().equals("")) {
            user.changePassword(passwordEncoder.encode(pw.trim()));
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userNo", user.getNo());
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public Boolean checkPassword(UserPasswordRequestDto request, String userId) {
        User user = findByUserId(userId);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    @Transactional
    public ApiResponse leave(String userId) {
        ApiResponse response = new ApiResponse();

        User user = findByUserId(userId);
        response.putData("userNo", user.getNo());
        userRepository.delete(user);

        int statusCode = StatusCode.SUCCESS;
        String message = ResponseMessage.SUCCESS;

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    private User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다. userId: " + userId));
    }
}
