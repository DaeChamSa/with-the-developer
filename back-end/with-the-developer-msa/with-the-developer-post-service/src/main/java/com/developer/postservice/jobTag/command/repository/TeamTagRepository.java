package com.developer.postservice.jobTag.command.repository;

import com.developer.postservice.jobTag.command.entity.TeamTag;
import com.developer.postservice.jobTag.command.entity.TeamTag;
import com.developer.postservice.team.post.command.entity.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {
    List<TeamTag> findAllByTeamPost(TeamPost teamPost);
}
