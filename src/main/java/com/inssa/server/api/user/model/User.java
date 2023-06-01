package com.inssa.server.api.user.model;

import com.inssa.server.api.image.model.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String nickname;

    @Column(nullable = false, length = 100, unique = true)
    private String phone;

    @Column(nullable = false)
    @ColumnDefault("'Y'")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileNo", insertable = false, updatable = false)
    private Image profile;
    private Long profileNo;

    @CreatedDate
    private LocalDateTime createdDate;

    private LocalDateTime quitDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumRole role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Builder
    public User(String userId, String email, String password, String nickname, String phone, EnumRole role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.role = role;
    }

    public User changeInfo(String email, String nickname, String phone) {
        this.email = email != null ? email : this.email;
        this.nickname = nickname != null ? nickname : this.nickname;
        this.phone = phone != null ? phone : this.phone;

        return this;
    }

    public User changePassword(String password) {
        this.password = password;
        return this;
    }
}
