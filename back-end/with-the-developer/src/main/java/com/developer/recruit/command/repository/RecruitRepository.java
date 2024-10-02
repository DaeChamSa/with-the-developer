package com.developer.recruit.command.repository;

import com.developer.recruit.command.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    @Query("SELECT MAX(r.recruitCode) FROM Recruit r")
    Long findLatestRecruitCode();
}
