package com.inssa.server.api.user.service;

import com.inssa.server.api.user.data.UserRepository;
import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserPasswordRequestDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.api.user.model.EnumRole;
import com.inssa.server.api.user.model.User;
import com.inssa.server.common.code.StatusCode;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.common.response.ApiResponse;
import com.inssa.server.common.response.ResponseMessage;
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
    public UserDetails loadUserByUsername(String userNo) throws UsernameNotFoundException {
        User user = findByUserNo(Long.parseLong(userNo));

        // 로그인 유저의 권한 목록 주입
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleKey()));

        return new AuthUser(user.getNo(), user.getPassword(), authorities);
    }

    public String login(UserRequestDto request) {
        User user = findByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InssaException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }

    @Transactional
    public Long register(UserRegisterRequestDto request) {

        User registerUser = userRepository.save(User.builder()
                                                    .password(passwordEncoder.encode(request.getPassword().trim()))
                                                    .email(request.getEmail())
                                                    .nickname(request.getNickname())
                                                    .role(EnumRole.USER)
                                                    .build());

        return registerUser.getNo();
    }

    public Boolean existsEmail(String email) {
        if(email == null || email.equals("")) {
            throw new InssaException("이메일을 입력해 주세요");
        }

        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User changeUserInfo(UserChangeInfoRequestDto request, Long userNo) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        User user = findByUserNo(userNo);
        user.changeInfo(request.getNickname());

        userRepository.save(user);

        return user;
    }

    @Transactional
    public Long changePassword(UserPasswordRequestDto request, Long userNo) {
        User user = findByUserNo(userNo);
        String pw = request.getPassword();
        if (pw != null && !pw.trim().equals("")) {
            user.changePassword(passwordEncoder.encode(pw.trim()));
        }

        userRepository.save(user);

        return userNo;
    }

    public Boolean checkPassword(UserPasswordRequestDto request, Long userNo) {
        User user = findByUserNo(userNo);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    @Transactional
    public void leave(Long userNo) {
        User user = findByUserNo(userNo);
        user.leave();

        userRepository.save(user);
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InssaException("해당 유저가 존재하지 않습니다. email: " + email));
    }

    private User findByUserNo(Long userNo) {
        return userRepository.findById(userNo)
                .orElseThrow(() -> new InssaException("해당 유저가 존재하지 않습니다. userNo: " + userNo));
    }
}
