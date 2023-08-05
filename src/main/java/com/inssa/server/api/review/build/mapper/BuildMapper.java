package com.inssa.server.api.review.build.mapper;

import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.api.review.build.dto.BuildUpdateRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildMapper {

    List<BuildRequestDto> selectList();

    Long insertBuild(BuildRequestDto request);

    Long updateBuild(BuildRequestDto request);

    Long deleteBuild(BuildRequestDto request);

    BuildRequestDto selectDetail(Long buildNo);
}
