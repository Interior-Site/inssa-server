package com.inssa.server.api.company.data;

import com.inssa.server.api.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
}
