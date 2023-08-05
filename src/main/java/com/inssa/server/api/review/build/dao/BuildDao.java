package com.inssa.server.api.review.build.dao;


import com.inssa.server.api.review.build.dto.BuildListResponseDto;
import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.api.review.build.mapper.BuildMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BuildDao {

    private final BuildMapper buildMapper;

    public Page<Map<String, Object>> selectList(Map<String, Object> paramMap, Pageable pageable) {

        paramMap.put("offset", pageable.getOffset());
        paramMap.put("pageSize", pageable.getPageSize());

        List<Map<String, Object>> builds = buildMapper.selectList(paramMap);
        int count = buildMapper.selectListCount(paramMap);

        return new PageImpl<>(builds, pageable, count);
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
