package com.inssa.server.api.review.build.mapper;

import com.inssa.server.api.review.build.dto.BuildRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface BuildMapper {

    List<Map<String, Object>> selectList(Map<String, Object> paramMap);

    int selectListCount(Map<String, Object> paramMap);

    Long insertBuild(BuildRequestDto request);

    Long updateBuild(BuildRequestDto request);

    Long deleteBuild(BuildRequestDto request);

    BuildRequestDto selectDetail(Long buildNo);
}
