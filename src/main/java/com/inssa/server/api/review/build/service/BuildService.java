package com.inssa.server.api.review.build.service;

import com.inssa.server.api.review.build.dao.BuildDao;
import com.inssa.server.api.review.build.dto.BuildListResponseDto;
import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

@Service("BuildService")
@RequiredArgsConstructor
public class BuildService {

    private final BuildDao builddao;

    public Page<Map<String, Object>> selectList(Map<String, Object> paramMap, Pageable pageable){

       // List<BuildRequestDto> buildList = builddao.selectList(pageable);

        return builddao.selectList(paramMap, pageable);
    }


    public Long insertBuild(BuildRequestDto request){

        Long result = builddao.insertBuild(request);


        return result;
    }

    public Long updateBuild(BuildRequestDto request) {

        Long result = builddao.updateBuild(request);

        return result;

    }

    public Long deleteBuild(BuildRequestDto request) {

        Long result = builddao.deleteBuild(request);

        return result;
    }

    public BuildRequestDto selectDetail(Long buildNo) {

        BuildRequestDto buildDetail = builddao.selectDetail(buildNo);


        return buildDetail;
    }
}
