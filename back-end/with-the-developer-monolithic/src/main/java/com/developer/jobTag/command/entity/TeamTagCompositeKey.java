package com.developer.jobTag.command.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

// Team Tag 복합키
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TeamTagCompositeKey implements Serializable {

    private Long teamPostCode;

    private Long jobTagCode;
}
