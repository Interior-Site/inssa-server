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

    public BuildRequestDto selectDetail(Long buildNo) {
        return buildMapper.selectDetail(buildNo);
    }

    public Long insertBuild(BuildRequestDto request) {
        Long result = buildMapper.insertBuild(request);

        return result;
    }

    public Long updateBuild(BuildRequestDto request){

        Long result = buildMapper.updateBuild(request);

        Long buildNo = null;
        if (result > 0) {
            buildNo = request.getBuildNo();
        }

        return buildNo;
    }

    public Long deleteBuild(BuildRequestDto request){

        Long result = buildMapper.deleteBuild(request);

        Long buildNo = null;
        if (result > 0) {
            buildNo = request.getBuildNo();
        }
        return buildNo;
    }
}
