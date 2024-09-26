package com.developer.teampost.command.repository;

import com.developer.teampost.command.entity.TeamPost;
import com.developer.user.command.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamPostRepository extends JpaRepository<TeamPost, Long> {
}
