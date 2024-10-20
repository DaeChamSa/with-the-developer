package com.developer.postservice.comu.post.command.repository;

import com.developer.postservice.comu.post.command.entity.ComuPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuPostRepository extends JpaRepository<ComuPost, Long> {
}
