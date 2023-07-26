package com.inssa.server.api.review.build.service;

import com.inssa.server.api.review.build.dao.BuildDao;
import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.api.review.build.dto.BuildUpdateDto;
import com.inssa.server.common.code.StatusCode;
import com.inssa.server.common.response.ApiResponse;
import com.inssa.server.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BuildService")
@RequiredArgsConstructor
public class BuildService {

    private final BuildDao builddao;

    public List<BuildDto> selectList(){

        List<BuildDto> buildList = builddao.selectList();

        return buildList;
    }


    public int insertBuild(BuildDto request, Long userNo){

        int result = builddao.insertBuild(request, userNo);

        return result;
    }

    public int updateBuild(BuildUpdateDto buildUpdateDto, Long userNo) {

        int result = builddao.updateBuild(buildUpdateDto, userNo);

        return result;

    }

    public int deleteBuild(int buildNo, Long userNo) {

        int result = builddao.deleteBuild(buildNo, userNo);

        return result;
    }

    public BuildDto selectDetail(int buildNo) {

        BuildDto buildDetail = builddao.selectDetail(buildNo);


        return buildDetail;
    }
}
