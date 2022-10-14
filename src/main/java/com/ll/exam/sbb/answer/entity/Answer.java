package com.ll.exam.sbb.answer.entity;

import com.ll.exam.sbb.aritlce.entity.Article;
import com.ll.exam.sbb.base.config.BaseTimeEntity;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne
    private Article article;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;


}
