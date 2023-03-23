package com.inssa.server.api.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "업체 API")
public class CompanyDto {
    @Schema(description = "업체 고유 번호")
    private int companyNo;
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
    @Schema(description = "업체 아이디")
    private String userId;
}
