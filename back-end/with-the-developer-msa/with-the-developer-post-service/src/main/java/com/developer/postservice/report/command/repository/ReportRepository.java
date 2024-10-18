package com.developer.postservice.report.command.repository;

import com.developer.postservice.comu.post.command.entity.ComuPost;
import com.developer.postservice.project.post.command.domain.aggregate.ProjPost;
import com.developer.postservice.recruit.command.entity.Recruit;
import com.developer.postservice.team.post.command.entity.TeamPost;
import com.developer.postservice.project.post.command.domain.aggregate.ProjPost;
import com.developer.postservice.recruit.command.entity.Recruit;
import com.developer.postservice.report.command.entity.Report;
import com.developer.postservice.team.post.command.entity.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Long countByRecruit(Recruit recruit);
    Long countByComuPost(ComuPost comuPost);
    Long countByTeamPost(TeamPost teamPost);
    Long countByProjPost(ProjPost projPost);

    List<Report> findByComuPost(ComuPost comuPost);
    List<Report> findByRecruit(Recruit recruit);
    List<Report> findByProjPost(ProjPost projPost);
    List<Report> findByTeamPost(TeamPost teamPost);
}
