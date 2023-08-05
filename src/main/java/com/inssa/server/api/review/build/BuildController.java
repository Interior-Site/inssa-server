package com.inssa.server.api.review.build;

import com.inssa.server.api.review.build.dto.BuildCreateRequestDto;
import com.inssa.server.api.review.build.dto.BuildListResponseDto;
import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.api.review.build.dto.BuildUpdateRequestDto;
import com.inssa.server.api.review.build.service.BuildService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "buildReview", description = "시공후기 API")
public class BuildController {

    private final BuildService buildService;

    @Operation(summary = "시공후기 목록조회 API", tags = "buildReview")
    @GetMapping("/builds")
    public InssaApiResponse<Map<String, Object>> buildList(@RequestParam Map<String, Object> parapMap,
            @SortDefault(sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable){

        Map<String, Object> resultMap = new HashMap<String, Object>();
        Page<Map<String, Object>> result = buildService.selectList(parapMap, pageable);

        resultMap.put("pages", result);
        resultMap.put("size", pageable.getPageSize());

        return InssaApiResponse.ok(resultMap);
    }

    @Operation(summary = "시공후기 상세조회 API", tags = "buildReview")
    @GetMapping("/build")
    public InssaApiResponse buildDetail(@RequestParam Long buildNo){

        return InssaApiResponse.ok(buildService.selectDetail(buildNo));
    }


    @Operation(summary = "시공후기 작성 API", tags = "buildReview")
    @PreAuthorizeLogInUser
    @PostMapping("/build")
    public InssaApiResponse<Map<String, Object>> insertBuild(@RequestBody BuildCreateRequestDto request, @AuthenticationPrincipal AuthUser user){
        BuildRequestDto serviceRequest = BuildRequestDto.createBuilder()
                .buildType(request.getBuildType())
                .categoryNo(request.getCategoryNo())
                .title(request.getTitle())
                .content(request.getContent())
                .userNo(user.getUserNo())
                .build();

        Long result = buildService.insertBuild(serviceRequest);
        return InssaApiResponse.ok(ResponseCode.CREATED, Map.of("result", result));
    }

    @Operation(summary = "시공후기 수정 API", tags = "buildReview")
    @PreAuthorizeLogInUser
    @PutMapping("/build")
    public InssaApiResponse<Map<String, Object>> updateBuild(@RequestBody BuildUpdateRequestDto request, @AuthenticationPrincipal AuthUser user){
        BuildRequestDto serviceRequest = BuildRequestDto.updateBuilder()
                .buildNo(request.getBuildNo())
                .title(request.getTitle())
                .content(request.getContent())
                .userNo(user.getUserNo())
                .build();
        Long buildNo = buildService.updateBuild(serviceRequest);
        return InssaApiResponse.ok(ResponseCode.UPDATED, Map.of("buildNo", buildNo));
    }

    @Operation(summary = "시공후기 삭제 API", tags = "buildReview")
    @PreAuthorizeLogInUser
    @PutMapping("/build/{buildNo}")
    public InssaApiResponse<Map<String, Object>> deleteBuild(@PathVariable Long buildNo, @AuthenticationPrincipal AuthUser user){
        BuildRequestDto serviceRequest = BuildRequestDto.deleteBuilder()
                .buildNo(buildNo)
                .userNo(user.getUserNo())
                .build();
        buildNo = buildService.deleteBuild(serviceRequest);
        return InssaApiResponse.ok(ResponseCode.DELETED, Map.of("buildNo", buildNo));
    }
}
