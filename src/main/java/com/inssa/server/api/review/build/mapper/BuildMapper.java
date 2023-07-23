package com.inssa.server.api.review.build.mapper;

import com.inssa.server.api.review.build.dto.BuildDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildMapper {

    List<BuildDto> selectList();
}
