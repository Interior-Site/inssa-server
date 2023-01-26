package com.inssa.server.api.test.dao;

import com.inssa.server.api.test.dto.TestDto;
import com.inssa.server.api.test.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestDao {
    private final TestMapper testMapper;

    public List<TestDto> findTest() {
        return testMapper.findTest();
    }
}
