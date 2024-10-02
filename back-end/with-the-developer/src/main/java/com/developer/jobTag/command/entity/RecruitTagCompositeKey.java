package com.developer.jobTag.command.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

// RecruitTag의 복합키 관련 클래스
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode  // 복합키 클래스를 정의할 때 equals()와 hashCode()가 꼭 필요하다.
public class RecruitTagCompositeKey implements Serializable { // 복합키 클래스를 정의할 때 serializable을 꼭 implements 해줘야 한다.

    private Long recruitCode;

    private Long jobTagCode;
}
