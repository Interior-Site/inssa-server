package com.inssa.server.api.review.build.dao;


import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.api.review.build.dto.BuildUpdateRequestDto;
import com.inssa.server.api.review.build.mapper.BuildMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BuildDao {

    private final BuildMapper buildMapper;

    public List<BuildRequestDto> selectList() {
        return buildMapper.selectList();
    }

    public BuildRequestDto selectDetail(int buildNo) {
        return buildMapper.selectDetail(buildNo);
    }

    public int insertBuild(BuildRequestDto request, Long userNo){
        return buildMapper.insertBuild(request, userNo);
    }

    public int updateBuild(BuildUpdateRequestDto buildUpdateDto, Long userNo){
        return buildMapper.updateBuild(buildUpdateDto, userNo);
    }

    public int deleteBuild(int buildNo, Long userNo){
        return buildMapper.deleteBuild(buildNo, userNo);
    }
}
