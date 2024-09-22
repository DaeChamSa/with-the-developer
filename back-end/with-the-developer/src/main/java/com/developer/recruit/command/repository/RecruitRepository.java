package com.developer.recruit.command.repository;

import com.developer.recruit.command.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
}
