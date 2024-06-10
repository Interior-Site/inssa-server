package com.inssa.server.api.user.user.service;
import com.inssa.server.api.user.social.dto.SocialUserRegisterRequestDto;
import com.inssa.server.api.user.social.model.SocialType;
import com.inssa.server.api.user.user.data.UserRepository;
import com.inssa.server.api.user.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.user.dto.UserPasswordRequestDto;
import com.inssa.server.api.user.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.user.dto.UserRequestDto;
import com.inssa.server.api.user.user.model.AuthUser;
import com.inssa.server.api.user.user.model.UserRole;
import com.inssa.server.api.user.user.model.User;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.common.response.ResponseCode;
import com.inssa.server.config.security.JwtTokenProvider;
import com.inssa.server.share.user.UserStatus;
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
        authorities.add(new SimpleGrantedAuthority(user.getRoleKey().replaceFirst("ROLE_", "")));

        return new AuthUser(user.getNo(), user.getEmail(), user.getPassword(), authorities);
    }

    public String login(UserRequestDto request) {
        User user = findByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InssaException("잘못된 비밀번호입니다.");
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleKey().replaceFirst("ROLE_", "")));

        return jwtTokenProvider.createToken(new UsernamePasswordAuthenticationToken(user.getNo(), user.getPassword(), authorities), user.getEmail());
    }

    @Transactional
    public Long register(UserRegisterRequestDto request) {

        // email 인증 구현 후 status 수정 필요
        User registerUser = userRepository.save(User.builder()
                                                    .password(passwordEncoder.encode(request.getPassword().trim()))
                                                    .email(request.getEmail())
                                                    .status(UserStatus.ACTIVATED)
                                                    .nickname(request.getNickname())
                                                    .role(UserRole.USER)
                                                    .socialType(SocialType.none)
                                                    .build());

        return registerUser.getNo();
    }

    @Transactional
    public String socialRegister(SocialUserRegisterRequestDto request) {
        User registerUser = userRepository.save(User.builder()
                                                    .password(passwordEncoder.encode(request.getSocialType().name()))
                                                    .email(request.getEmail())
                                                    .status(UserStatus.ACTIVATED)
                                                    .nickname(request.getNickname())
                                                    .role(UserRole.USER)
                                                    .socialType(request.getSocialType())
                                                    .build());

        // 소셜 회원가입의 경우 바로 로그인이 되도록 jwt 토큰 발급해야 함
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(registerUser.getRoleKey().replaceFirst("ROLE_", "")));
        return jwtTokenProvider.createToken(new UsernamePasswordAuthenticationToken(registerUser.getNo(), registerUser.getPassword(), authorities), registerUser.getEmail());
    }

    public Boolean existsEmail(String email) {
        if(email == null || email.isEmpty()) {
            throw new InssaException("이메일을 입력해 주세요");
        }

        return userRepository.existsByEmail(email);
    }

    public Boolean existsNickname(String nickname) {
        if(nickname == null || nickname.isEmpty()) {
            throw new InssaException("닉네임을 입력해 주세요");
        }

        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public User changeUserInfo(UserChangeInfoRequestDto request, Long userNo) {
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
