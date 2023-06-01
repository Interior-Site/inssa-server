package com.inssa.server.api.company.service;

import com.inssa.server.api.company.data.CompanyRepository;
import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.model.Company;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CompanyService")
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<CompanyResponseDto> findCompanyList() {
        return companyRepository.findCompanyList();
    }

    public CompanyResponseDto findCompany(Long companyNo) {

        Company company = findCompanyById(companyNo);

        return new CompanyResponseDto(company);
    }

    @Transactional
    public ApiResponse changeCompanyInfo(CompanyChangeInfoRequestDto request) {
        ApiResponse response = new ApiResponse();
        int statusCode;
        String message;

        Company company = findCompanyById(request.getCompanyNo());
        company.update(request.getCompanyName(), request.getContactNumber(), request.getStatus(), request.getApproval());

        statusCode = StatusCode.SUCCESS;
        message = ResponseMessage.SUCCESS;
        response.putData("companyNo", request.getCompanyNo());

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    @Transactional
	public ApiResponse deleteCompany(Long companyNo) {
        // 로그인한 유저가 해당 회사를 가지고 있는지 확인 로직 필요

        ApiResponse response = new ApiResponse();
        int statusCode;
        String message;

        companyRepository.deleteById(companyNo);

        statusCode = StatusCode.SUCCESS;
        message = ResponseMessage.SUCCESS;
        response.putData("companyNo", companyNo);

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    private Company findCompanyById(Long companyNo) {

        return companyRepository.findById(companyNo).orElseThrow(() -> new IllegalArgumentException("해당하는 업체를 찾을 수 없습니다. companyNo: " + companyNo));
    }
}
