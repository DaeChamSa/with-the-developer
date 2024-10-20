package com.developer.userservice.user.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.developer.userservice.user.command.domain.aggregate.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("select e from Email e where e.code = :code")
    Optional<Email> findByCode(@Param("code") String code);

}
