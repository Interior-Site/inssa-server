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


    public int insertBuild(BuildRequestDto request, Long userNo){

        int result = builddao.insertBuild(request, userNo);

        return result;
    }

    public int updateBuild(BuildUpdateRequestDto buildUpdateDto, Long userNo) {

        int result = builddao.updateBuild(buildUpdateDto, userNo);

        return result;

    }

    public int deleteBuild(int buildNo, Long userNo) {

        int result = builddao.deleteBuild(buildNo, userNo);

        return result;
    }

    public BuildRequestDto selectDetail(int buildNo) {

        BuildRequestDto buildDetail = builddao.selectDetail(buildNo);


        return buildDetail;
    }
}
