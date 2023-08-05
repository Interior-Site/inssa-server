package com.inssa.server.api.review.build.mapper;

import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.api.review.build.dto.BuildUpdateRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildMapper {

    List<BuildRequestDto> selectList();

    int insertBuild(BuildRequestDto request, Long userNo);

    int updateBuild(BuildUpdateRequestDto buildUpdateDto, Long userNo);

    int deleteBuild(int buildNo, Long userNo);

    BuildRequestDto selectDetail(int buildNo);
}
