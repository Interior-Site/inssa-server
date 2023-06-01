package com.inssa.server.api.company;

import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.service.CompanyService;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "company list", description = "업체 조회 API")
    @GetMapping("/company/list")
    public List<CompanyResponseDto> findTestCompanyList() {
        // 페이징 처리 진행해야 함
        return companyService.findCompanyList();
    }

    @Operation(summary = "company", description = "업체 단건 조회 API")
    @GetMapping("/company/{companyNo}")
    public CompanyResponseDto findCompany(@PathVariable Long companyNo) {
        return companyService.findCompany(companyNo);
    }

    @Operation(summary = "update company", description = "업체 수정 API")
    @PutMapping("/company")
    public ApiResponse changeCompanyInfo(@RequestBody CompanyChangeInfoRequestDto request) {
        return companyService.changeCompanyInfo(request);
    }

    @Operation(summary = "delete company", description = "업체 삭제 API")
    @DeleteMapping("/company/{companyNo}")
    public ApiResponse deleteCompany(@PathVariable Long companyNo) {
        return companyService.deleteCompany(companyNo);
    }
}
