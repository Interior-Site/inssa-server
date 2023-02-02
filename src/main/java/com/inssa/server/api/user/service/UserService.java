package com.inssa.server.api.user.service;

import com.inssa.server.api.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("UserService")
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
}
