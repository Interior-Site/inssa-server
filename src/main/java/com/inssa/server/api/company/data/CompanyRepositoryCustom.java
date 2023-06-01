package com.inssa.server.api.company.data;

import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepositoryCustom {
    List<CompanyResponseDto> findCompanyList();
}
