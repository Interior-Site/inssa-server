package com.inssa.server.api.company.service;

import com.inssa.server.api.company.dao.CompanyDao;
import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.Pagination;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CompanyService")
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDao companyDao;

    public List<CompanyDto> findCompanyList(Pagination paging) {
        return companyDao.findCompanyList(paging);
    }

    @Transactional
    public ApiResponse changeCompanyInfo(CompanyChangeInfoRequestDto request) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = companyDao.changeCompanyInfo(request);

        if(result > 0) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("companyNo", request.getCompanyNo());
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    @Transactional
	public ApiResponse deleteCompany(String companyNo) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = companyDao.deleteCompany(companyNo);

        if(result > 0) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("companyNo", companyNo);
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public CompanyDto findCompany(String companyNo) {
        return companyDao.findCompany(companyNo);
    }
}
