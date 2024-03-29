package com.inssa.server.api.user.user.model;

import com.inssa.server.api.image.model.Image;
import com.inssa.server.api.user.social.model.SocialType;
import com.inssa.server.share.entity.BaseTimeEntity;
import com.inssa.server.share.user.UserStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "user_uk01"),
                @UniqueConstraint(columnNames = "nickname", name = "user_uk02")
        }
)
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long no;
    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, length = 100)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Builder
    public User(String email, String password, UserStatus status, String nickname, UserRole role, SocialType socialType) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.nickname = nickname;
        this.role = role;
        this.socialType = socialType;
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

    public boolean isActive() {
        return Objects.equals(status, UserStatus.ACTIVATED)
                && Objects.isNull(quitDate)
                && !Objects.equals(role, UserRole.ANONYMOUS);
    }
}
