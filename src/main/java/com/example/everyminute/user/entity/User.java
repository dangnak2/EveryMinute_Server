package com.example.everyminute.user.entity;

import com.example.everyminute.global.entity.BaseEntity;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.user.dto.request.JoinReq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<Subscribe> subscribeList = new ArrayList<>();

    @Builder
    public User(String name, Role role, String email, String password) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public static User of(JoinReq joinReq) {
        return User.builder()
                .name(joinReq.getName())
                .email(joinReq.getEmail())
                .password(joinReq.getPassword())
                .role(Role.getRoleByName(joinReq.getRole()))
                .build();
    }

    public boolean checkRole(Role role) {
        return this.role == role;
    }

    public void addSubscribe(Subscribe subscribe) {
        this.subscribeList.add(subscribe);
    }

    public void cancelSubscribe(Subscribe subscribe) {
        this.subscribeList.remove(subscribe);
    }
}
