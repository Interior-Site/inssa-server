package com.inssa.server.api.test.mapper;

import com.inssa.server.api.test.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestDto> findTest();
}
