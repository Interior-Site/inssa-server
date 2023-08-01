package com.inssa.server.api.company.service;

import com.inssa.server.api.company.data.CompanyRepository;
import com.inssa.server.api.company.dto.CompanyChangeInfoRequestDto;
import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.model.Company;
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
    public Company changeCompanyInfo(CompanyChangeInfoRequestDto request) {
        Company company = findCompanyById(request.getCompanyNo());
        company.update(request.getCompanyName(), request.getContactNumber(), request.getStatus(), request.getApproval());

        return company;
    }

    @Transactional
	public void deleteCompany(Long companyNo) {
        companyRepository.deleteById(companyNo);
    }

    private Company findCompanyById(Long companyNo) {
        return companyRepository.findById(companyNo).orElseThrow(() -> new IllegalArgumentException("해당하는 업체를 찾을 수 없습니다. companyNo: " + companyNo));
    }
}
