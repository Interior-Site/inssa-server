package com.inssa.server.api.company;

import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.company.service.CompanyService;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "company", description = "업체 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "업체 조회 API", tags = "company")
    @GetMapping("/company/list")
    public InssaApiResponse<List<CompanyResponseDto>> findTestCompanyList() {
        // 페이징 처리 진행해야 함
        return InssaApiResponse.ok(companyService.findCompanyList());
    }

    @Operation(summary = "업체 단건 조회 API", tags = "company")
    @GetMapping("/company/{companyNo}")
    public InssaApiResponse<CompanyResponseDto> findCompany(@PathVariable Long companyNo) {
        return InssaApiResponse.ok(companyService.findCompany(companyNo));
    }

    @Operation(summary = "업체 수정 API", tags = "company")
    @PutMapping("/company")
    public InssaApiResponse<Map<String, Object>> changeCompanyInfo(@RequestBody CompanyChangeInfoRequestDto request) {
        Company company = companyService.changeCompanyInfo(request);
        return InssaApiResponse.ok(ResponseCode.UPDATED, Map.of("companyNo", company.getNo()));
    }

    @Operation(summary = "업체 삭제 API", tags = "company")
    @DeleteMapping("/company/{companyNo}")
    public InssaApiResponse<Map<String, Object>> deleteCompany(@PathVariable Long companyNo) {
        companyService.deleteCompany(companyNo);
        return InssaApiResponse.ok(ResponseCode.DELETED, Map.of("companyNo", companyNo));
    }
}
