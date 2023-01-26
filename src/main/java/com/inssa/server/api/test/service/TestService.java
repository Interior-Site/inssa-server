package com.inssa.server.api.test.service;

import com.inssa.server.api.test.dao.TestDao;
import com.inssa.server.api.test.dto.TestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TestService")
@RequiredArgsConstructor
public class TestService {

    private final TestDao testDao;

    public List<TestDto> findTest() {
        return testDao.findTest();
    }
}
