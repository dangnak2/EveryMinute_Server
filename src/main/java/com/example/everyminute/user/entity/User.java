package com.example.everyminute.user.entity;

import com.example.everyminute.global.entity.BaseEntity;
import com.example.everyminute.user.dto.request.JoinUserReq;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Size(max = 30)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @NotNull
    @Size(max = 30)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @Builder
    public User(String name, Role role, String email, String password) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public static User toEntity(JoinUserReq joinUserReq) {
        return User.builder()
                .name(joinUserReq.getName())
                .email(joinUserReq.getEmail())
                .password(joinUserReq.getPassword())
                .role(Role.getRoleByName(joinUserReq.getRole()))
                .build();
    }
}
