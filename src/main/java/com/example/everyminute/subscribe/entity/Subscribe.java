package com.example.everyminute.subscribe.entity;

import com.example.everyminute.global.entity.BaseEntity;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscribe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscribeId;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}
