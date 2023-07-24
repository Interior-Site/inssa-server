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



    public ApiResponse selectList(){
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BuildDto> buildList = builddao.selectList();

        if(!buildList.isEmpty()){
            statusCode = StatusCode.SUCCESS;
            message = "시공후기 게시글 목록 조회 성공" + ResponseMessage.SUCCESS;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("result", buildList);
        return response;
    }


    public ApiResponse insertBuild(BuildDto request, Long userNo){
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = builddao.insertBuild(request, userNo);

        if(result > 0){
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", build);
        }

        // 응답에 결과코드, 메세지 담음
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public ApiResponse updateBuild(BuildUpdateDto buildUpdateDto, Long userNo) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = builddao.updateBuild(buildUpdateDto, userNo);

        if(result > 0){
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", build);

        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;

    }

    public ApiResponse deleteBuild(int buildNo, Long userNo) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = builddao.deleteBuild(buildNo, userNo);

        if(result > 0){
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", build);

        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public ApiResponse selectDetail(int buildNo) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        BuildDto buildDetail = builddao.selectDetail(buildNo);

        if(!buildDetail.isEmpty()){
            statusCode = StatusCode.SUCCESS;
            message = "시공후기 상세 조회 성공" + ResponseMessage.SUCCESS;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("result", buildDetail);
        return response;
    }
}
