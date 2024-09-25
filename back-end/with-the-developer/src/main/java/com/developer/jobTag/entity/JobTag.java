package com.developer.jobTag.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class JobTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobTagCode; // 직무태그 코드

    @NotNull
    private String jobTagName;  // 직무 태그 이름

     public JobTag(String jobTagName) {
         this.jobTagName = jobTagName;
     }
}
