package com.developer.jobTag.repository;

import com.developer.jobTag.entity.TeamTag;
import com.developer.team.post.command.entity.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {
    List<TeamTag> findAllByTeamPost(TeamPost teamPost);
}
