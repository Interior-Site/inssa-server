package com.inssa.server.api.review.build.dao;


import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.api.review.build.dto.BuildUpdateDto;
import com.inssa.server.api.review.build.mapper.BuildMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BuildDao {

    private final BuildMapper buildMapper;

    public List<BuildDto> selectList() {
        return buildMapper.selectList();
    }

    public BuildDto selectDetail(int buildNo) {
        return buildMapper.selectDetail(buildNo);
    }

    public int insertBuild(BuildDto request, Long userNo){
        return buildMapper.insertBuild(request, userNo);
    }

    public int updateBuild(BuildUpdateDto buildUpdateDto, Long userNo){
        return buildMapper.updateBuild(buildUpdateDto, userNo);
    }

    public int deleteBuild(int buildNo, Long userNo){
        return buildMapper.deleteBuild(buildNo, userNo);
    }
}
