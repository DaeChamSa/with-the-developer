package com.developer.jobTag.command.entity;

import com.developer.team.post.command.entity.TeamPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_tag")
@NoArgsConstructor
@Getter
public class TeamTag {

    @EmbeddedId
    private TeamTagCompositeKey code;

    @ManyToOne
    @MapsId("teamPostCode")
    @JoinColumn(name = "team_post_code")
    private TeamPost teamPost;

    @ManyToOne
    @MapsId("jobTagCode")
    @JoinColumn(name = "job_tag_code")
    private JobTag jobTag;

    public TeamTag(TeamPost teamPost, JobTag jobTag) {

        this.teamPost = teamPost;
        this.jobTag = jobTag;
        this.code = new TeamTagCompositeKey(teamPost.getTeamPostCode(), jobTag.getJobTagCode());
    }

    public void updateTeamPost(TeamPost teamPost) {

        this.teamPost = teamPost;
        this.code.setTeamPostCode(teamPost.getTeamPostCode());
    }
}
