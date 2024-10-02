package com.developer.prefix.command.domain.aggregate;

import com.developer.prefix.command.application.dto.PrefixCreateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Table(name = "prefix")
@Entity
@NoArgsConstructor
public class Prefix {

    @Id
    @Column(name = "user_code")
    @JoinColumn(name = "user_code")
    private Long userCode;

    @Column(name = "job_tag_code")
    @JoinColumn(name = "job_tag_code")
    private Long jobTagCode;

    @Column(name = "dbti_code")
    @JoinColumn(name = "dbti_code")
    private Long dbtiCode;

    private Prefix(Long userCode, PrefixCreateDTO dto) {

        this.userCode = userCode;
        this.jobTagCode = dto.getJobTagCode();
        this.dbtiCode = dto.getDbtiCode();
    }

    public static Prefix prefixCreate(Long userCode, PrefixCreateDTO prefixCreateDTO) {

        return new Prefix(userCode, prefixCreateDTO);
    }
}
