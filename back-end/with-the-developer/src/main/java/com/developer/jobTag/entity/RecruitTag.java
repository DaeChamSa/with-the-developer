package com.developer.jobTag.entity;

import com.developer.recruit.command.entity.Recruit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecruitTag {

    @EmbeddedId
    private RecruitTagCompositeKey code;  // 복합키

    @ManyToOne
    @MapsId("recruitCode")
    @JoinColumn(name = "recruit_code")
    private Recruit recruit;

    @ManyToOne
    @MapsId("jobTagCode")
    @JoinColumn(name = "job_tag_code")
    private JobTag jobTag;
}
