package com.developer.postservice.project.comment.command.domain.repository;

import com.developer.postservice.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.postservice.project.comment.command.domain.aggregate.ProjCmt;

import java.util.Optional;

public interface ProjCmtRepository {

    ProjCmt save(ProjCmt projCmt);

    Optional<ProjCmt> findById(Long projCmtCode);

    void deleteById(Long projCmtCode);
}
