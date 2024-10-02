package com.developer.jobTag.command.entity;

import com.developer.recruit.command.entity.Recruit;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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

    public RecruitTag(Recruit recruit, JobTag jobTag) {
        this.recruit = recruit;
        this.jobTag = jobTag;
        this.code = new RecruitTagCompositeKey(recruit.getRecruitCode(), jobTag.getJobTagCode());
    }

    public void updateRecruit(Recruit recruit) {
        this.recruit = recruit;
    }

}
