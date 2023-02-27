package com.inssa.server.api.user.service;

import com.inssa.server.api.user.dao.UserDao;
import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import com.inssa.server.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDto user = userDao.findByUserId(userId);

        if(user == null) {
            throw new UsernameNotFoundException("해당 유저가 없습니다");
        }

        return user;
    }

    public String login(UserRequestDto request) {
        UserDto user = userDao.findByUserId(request.getUserId());

        if(user == null) {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }

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

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        int result = userDao.register(request);

        if(result > 0) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", request.getUserId());
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public Boolean existsUserId(String userId) {
        if(userId == null || userId.equals("")) {
            throw new IllegalArgumentException("아이디를 입력해 주세요");
        }

        int result = userDao.existsUserId(userId);

        if(result > 0) {
            return true;
        }

        return false;
    }

    @Transactional
    public ApiResponse changeUserInfo(UserChangeInfoRequestDto request) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = userDao.changeUserInfo(request);

        if(result > 0) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", request.getUserId());
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public ApiResponse changePassword(UserRequestDto request) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        int result = userDao.changePassword(request);

        if(result > 0) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", request.getUserId());
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public Boolean checkPassword(UserRequestDto request) {
        UserDto user = userDao.findByUserId(request.getUserId());

        if(user == null) {
            throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
        }

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    public Boolean leave(String userId) {
        int result = userDao.leave(userId);

        if(result > 0) {
            return true;
        }
        return false;
    }
}
