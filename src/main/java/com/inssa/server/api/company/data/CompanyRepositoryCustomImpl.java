package com.inssa.server.api.company.data;

import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.model.QCompany;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<CompanyResponseDto> findCompanyList() {
        JPAQuery<CompanyResponseDto> query = jpaQueryFactory.select(constructor(
                        CompanyResponseDto.class,
                        QCompany.company.id,
                        QCompany.company.registrationNo,
                        QCompany.company.companyName,
                        QCompany.company.contactNumber,
                        QCompany.company.status,
                        QCompany.company.approval,
                        QCompany.company.rating,
                        QCompany.company.userNo
                ))
                .from(QCompany.company);

        return query.fetch();
    }
}
