package com.inssa.server.api.company.dao;

import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyDto;
import com.inssa.server.api.company.mapper.CompanyMapper;
import com.inssa.server.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyDao {
    private final CompanyMapper companyMapper;

    public List<CompanyDto> findCompanyList(Pagination paging) {
        return companyMapper.findCompanyList(paging);
    }

    public int changeCompanyInfo(CompanyChangeInfoRequestDto request) {
        return companyMapper.changeCompanyInfo(request);
    }

	public int deleteCompany(String companyNo) {
		return companyMapper.deleteCompany(companyNo);
	}

	public CompanyDto findCompany(String companyNo) {
		return companyMapper.findCompany(companyNo);
	}
}
