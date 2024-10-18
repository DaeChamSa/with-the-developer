package com.developer.postservice.team.comment.command.repository;

import com.developer.postservice.team.comment.command.entity.TeamCmt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamCmtRepository extends JpaRepository<TeamCmt, Long> {
}
