package com.inssa.server.api.review.build.mapper;

import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.api.review.build.dto.BuildUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildMapper {

    List<BuildDto> selectList();

    int insertBuild(BuildDto build);

    int updateBuild(BuildUpdateDto buildUpdateDto, Long userNo);

    int deleteBuild(int buildNo, Long userNo);

    BuildDto selectDetail(int buildNo);
}
