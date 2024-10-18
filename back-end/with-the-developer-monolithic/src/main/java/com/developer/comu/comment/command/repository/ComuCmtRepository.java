package com.developer.comu.comment.command.repository;

import com.developer.comu.comment.command.entity.ComuCmt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuCmtRepository extends JpaRepository<ComuCmt, Long> {

}
