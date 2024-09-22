package com.developer.project.command.domain.repository;

import com.developer.project.command.domain.aggregate.ProjPost;

import java.util.Optional;

public interface ProjPostRepository {

    ProjPost save(ProjPost projPost);

    Optional<ProjPost> findById(Long projPostCode);

    void deleteById(Long projPostCode);
}
