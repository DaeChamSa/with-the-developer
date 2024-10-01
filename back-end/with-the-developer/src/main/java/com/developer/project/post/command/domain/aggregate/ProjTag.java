package com.developer.project.post.command.domain.aggregate;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "proj_tag")
@Getter
public class ProjTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projPostTagCode;

    private String tagContent;

    @ManyToOne
    @JoinColumn(name = "proj_post_code")
    private ProjPost projPost;

    @Builder
    public ProjTag(String tagContent, ProjPost projPost) {

        this.tagContent = tagContent;
        this.projPost = projPost;
    }

    public ProjTag() {

    }
}
