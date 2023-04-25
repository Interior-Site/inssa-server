package com.inssa.server.api.company.mapper;

import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyDto;
import com.inssa.server.common.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {
    List<CompanyDto> findCompanyList(Pagination paging);

    int changeCompanyInfo(CompanyChangeInfoRequestDto request);

	int deleteCompany(String companyNo);
}
