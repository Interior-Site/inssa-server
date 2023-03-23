package com.inssa.server.api.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "업체 수정 API request")
public class CompanyChangeInfoRequestDto {
    @Schema(description = "업체 고유 번호")
    private int companyNo;
    @Schema(description = "업체명")
    private String companyName;
    @Schema(description = "업체 전화번호")
    private String contactNumber;
    @Schema(description = "운영 여부")
    private String status;
    @Schema(description = "승인 여부")
    private String approval;
}
