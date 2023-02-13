package com.inssa.server.api.user.service;

import com.inssa.server.api.user.dao.UserDao;
import com.inssa.server.api.user.dto.UserDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import com.inssa.server.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("UserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userDao.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("해당 유저가 없습니다");
        }

        return user;
    }

    public String login(UserRequestDto request) {
        UserDto user = userDao.findByEmail(request.getEmail());

        if(user == null) {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }

        if(!passwordEncoder.matches(user.getPassword(), request.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    }
}
