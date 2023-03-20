package com.inssa.server.api.company.dto;

import lombok.Data;

@Data
public class CompanyDto {
    private int companyNo;
    private String registrationNo;
    private String companyName;
    private String contactNumber;
    private String status;
    private String approval;
    private String rating;
    private String userId;
}
