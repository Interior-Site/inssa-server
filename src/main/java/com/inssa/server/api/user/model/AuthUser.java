package com.inssa.server.api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AuthUser implements UserDetails {

    private Long userNo;
    private String password;
    private ArrayList<GrantedAuthority> authorities;

    public AuthUser(Long userNo, String password, ArrayList<GrantedAuthority> authorities) {
        this.userNo = userNo;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore // 데이터 값 숨기기
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userNo.toString();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}