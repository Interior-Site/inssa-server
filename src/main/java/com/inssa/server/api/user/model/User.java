package com.inssa.server.api.user.model;

import com.inssa.server.api.image.model.Image;
import com.inssa.server.common.entity.BaseTimeEntity;
import com.inssa.server.share.user.UserStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long no;
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileNo", insertable = false, updatable = false)
    private Image profile;
    private Long profileNo;

    private LocalDateTime quitDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Builder
    public User(String email, String password, UserStatus status, String nickname, UserRole role) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.nickname = nickname;
        this.role = role;
    }

    public void changeInfo(String nickname) {
        this.nickname = nickname != null ? nickname : this.nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeUserStatus(UserStatus status) {
        this.status = status;
    }

    public void leave() {
        this.status = UserStatus.LEFT;
        quitDate = LocalDateTime.now();
    }
}
