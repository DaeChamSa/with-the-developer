package com.developer.team.comment.command.repository;

import com.developer.team.comment.command.entity.TeamCmt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamCmtRepository extends JpaRepository<TeamCmt, Long> {
}
