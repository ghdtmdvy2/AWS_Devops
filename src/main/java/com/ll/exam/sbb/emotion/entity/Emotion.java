package com.ll.exam.sbb.emotion.entity;

import com.ll.exam.sbb.analysis.entity.Analysis;
import com.ll.exam.sbb.base.config.BaseTimeEntity;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Emotion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Analysis analysis;

    @ManyToOne
    private SiteUser author;

    @Column
    private double angry = 24.4;

    @Column
    private double neutral = 25.5;

    @Column
    private double happy = 50.1;
}
