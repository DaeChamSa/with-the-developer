package com.developer.teampost.command.repository;

import com.developer.teampost.command.entity.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPostRepository extends JpaRepository<TeamPost, Long> {
}
