package com.developer.postservice.team.post.command.repository;

import com.developer.postservice.team.post.command.entity.TeamPost;
import com.developer.postservice.team.post.command.entity.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPostRepository extends JpaRepository<TeamPost, Long> {
}
