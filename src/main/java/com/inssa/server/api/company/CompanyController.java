package com.inssa.server.api.company;

import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyDto;
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
    public List<CompanyDto> findCompanyList(
            @RequestParam
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            int page,
            @RequestParam
            @Parameter(description = "조회 사이즈", example = "10")
            int size) {
        Pagination paging = new Pagination().paging(page, size);

        return companyService.findCompanyList(paging);
    }

    @Operation(summary = "update company", description = "업체 수정 API")
    @PutMapping("/company")
    public ApiResponse changeCompanyInfo(@RequestBody CompanyChangeInfoRequestDto request) {
        return companyService.changeCompanyInfo(request);
    }

    @Operation(summary = "delete company", description = "업체 삭제 API")
    @DeleteMapping("/company/{companyNo}")
    public ApiResponse deleteCompany(@PathVariable String companyNo) {
        return companyService.deleteCompany(companyNo);
    }
}
