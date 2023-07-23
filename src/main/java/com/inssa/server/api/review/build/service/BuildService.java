package com.inssa.server.api.review.build.service;

import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.api.review.build.dao.BuildDao;
import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import com.inssa.server.common.code.StatusCode;
import com.inssa.server.common.response.ApiResponse;
import com.inssa.server.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service("BuildService")
@RequiredArgsConstructor
public class BuildService {

    private final BuildDao builddao;

    private final BuildDto builddto;


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


    public ApiResponse insertBuild(BuildDto build){
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = builddao.insertBuild(build);

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

    public ApiResponse updateBuild(BuildDto build) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = builddao.updateBuild(build);

        if(result > 0){
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", build);

        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;

    }

    public ApiResponse deleteBuild(BuildDto build) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = builddao.deleteBuild(build);

        if(result > 0){
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", build);

        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public ApiResponse selectDetail() {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        builddto buildDetail = builddao.selectDetail();

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
