package com.inssa.server.api.company.service;

import com.inssa.server.api.company.dao.CompanyDao;
import com.inssa.server.api.company.dto.CompanyDto;
import com.inssa.server.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CompanyService")
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDao companyDao;

    public List<CompanyDto> findCompanyList(Pagination paging) {
        return companyDao.findCompanyList(paging);
    }
}
