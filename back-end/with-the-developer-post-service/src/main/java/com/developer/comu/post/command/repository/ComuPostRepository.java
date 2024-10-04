package com.developer.comu.post.command.repository;

import com.developer.comu.post.command.entity.ComuPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuPostRepository extends JpaRepository<ComuPost, Long> {
}
