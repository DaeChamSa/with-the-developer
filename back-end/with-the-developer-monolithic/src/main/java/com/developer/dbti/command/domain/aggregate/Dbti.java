package com.developer.dbti.command.domain.aggregate;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "dbti")
@Entity
@NoArgsConstructor
@Valid
public class Dbti {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dbti_code")
    private Long dbtiCode;

    // 성향(수식어) ~~하는 등
    @NotNull
    @Column(name = "dbti_value")
    private String dbtiValue;

    @NotNull
    @Column(name = "dbti_role")
    @Enumerated(EnumType.STRING)
    private DbtiRole dbtiRole;

    public Dbti(String dbtiValue, DbtiRole dbtiRole) {
        this.dbtiValue = dbtiValue;
        this.dbtiRole = dbtiRole;
    }
}
