package com.inssa.server.api.company.model;

import com.inssa.server.api.user.model.User;
import com.inssa.server.common.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Company extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_no")
    private Long no;

    @Column(nullable = false)
    private String registrationNo;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    @ColumnDefault("'Y'")
    private String status;

    @Column(nullable = false)
    @ColumnDefault("'N'")
    private String approval;

    private String rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", insertable = false, updatable = false)
    private User user;
    private Long userNo;

    public Company update(String companyName, String contactNumber, String status, String approval) {
        if(isColumnToUpdate(companyName)) {
            this.companyName = companyName;
        }

        if(isColumnToUpdate(contactNumber)) {
            this.contactNumber = contactNumber;
        }

        if(isColumnToUpdate(status)) {
            this.status = status;
        }

        if(isColumnToUpdate(approval)) {
            this.approval = approval;
        }

        return this;
    }

    private Boolean isColumnToUpdate(String data) {
        if(data != null && !data.trim().equals("")) {
            return true;
        }

        return false;
    }
}
