package com.developer.command.repository;

import com.developer.command.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("select e from Email e where e.code = :code")
    Optional<Email> findByCode(String code);

}
