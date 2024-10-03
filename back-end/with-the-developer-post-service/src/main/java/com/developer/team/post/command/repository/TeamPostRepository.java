package com.developer.team.post.command.repository;

import com.developer.team.post.command.entity.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPostRepository extends JpaRepository<TeamPost, Long> {
}
