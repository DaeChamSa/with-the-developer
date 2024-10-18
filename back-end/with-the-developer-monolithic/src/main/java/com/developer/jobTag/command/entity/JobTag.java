package com.developer.jobTag.command.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "job_tag")
@Getter
@ToString
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
