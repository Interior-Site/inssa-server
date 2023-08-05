package com.inssa.server.api.review.build.service;

import com.inssa.server.api.review.build.dao.BuildDao;
import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.api.review.build.dto.BuildUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BuildService")
@RequiredArgsConstructor
public class BuildService {

    private final BuildDao builddao;

    public List<BuildRequestDto> selectList(){

        List<BuildRequestDto> buildList = builddao.selectList();

        return buildList;
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
