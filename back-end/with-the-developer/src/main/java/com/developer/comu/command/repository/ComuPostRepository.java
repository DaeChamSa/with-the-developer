package com.developer.comu.command.repository;


import com.developer.comu.command.entity.ComuPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuPostRepository extends JpaRepository<ComuPost, Long> {
}
