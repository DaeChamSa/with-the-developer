package com.developer.jobTag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobTagCode; // 직무태그 코드

    private String jobTagName;  // 직무 태그 이름
}
