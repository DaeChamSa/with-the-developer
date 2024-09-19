package com.developer.comu.command.entity;

import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name="comu_post")
@Table(name="comu_post")
@Getter
public class ComuPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comuCode;
    private String comuSubject;
    private String comuContent;
    @CreationTimestamp
    private LocalDateTime comuCreateDate;
    @UpdateTimestamp
    private LocalDateTime comuUpdateDate;
    private boolean comuDelStatus;

    @ManyToOne
    @JoinColumn(name="userCode")
    private User userCode;





}
