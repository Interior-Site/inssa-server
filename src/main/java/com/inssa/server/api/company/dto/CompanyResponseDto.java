package com.inssa.server.api.company.dto;

import com.inssa.server.api.company.model.Company;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "업체 API")
public class CompanyResponseDto {
    @Schema(description = "업체 고유 번호")
    private Long companyNo;
    @Schema(description = "사업자 등록 번호")
    private String registrationNo;
    @Schema(description = "업체명")
    private String companyName;
    @Schema(description = "업체 전화번호")
    private String contactNumber;
    @Schema(description = "운영 여부")
    private String status;
    @Schema(description = "승인 여부")
    private String approval;
    @Schema(description = "평점") // 평점의 경우 따로 테이블 생성해야 할 것 같음
    private String rating;
    @Schema(description = "업체 사용 아이디")
    private Long userNo;

    @QueryProjection
    public CompanyResponseDto(Long companyNo, String registrationNo, String companyName, String contactNumber, String status, String approval, String rating, Long userNo) {
        this.companyNo = companyNo;
        this.registrationNo = registrationNo;
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.status = status;
        this.approval = approval;
        this.rating = rating;
        this.userNo = userNo;
    }

    public CompanyResponseDto(Company company) {
        this.companyNo = company.getNo();
        this.registrationNo = company.getRegistrationNo();
        this.companyName = company.getCompanyName();
        this.contactNumber = company.getContactNumber();
        this.status = company.getStatus();
        this.approval = company.getApproval();
        this.rating = company.getRating();
        this.userNo = company.getUserNo();
    }
}
