package com.inssa.server.api.company.data;

import com.inssa.server.api.company.dto.CompanyResponseDto;

import java.util.List;

public interface CompanyRepositoryCustom {
    List<CompanyResponseDto> findCompanyList();
}
