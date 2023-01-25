package com.ll.exam.sbb.emotion.entity;

import com.ll.exam.sbb.analysis.entity.Analysis;
import com.ll.exam.sbb.base.config.BaseTimeEntity;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Analysis analysis;

    @ManyToOne
    private SiteUser author;

    @Column
    private double angry;

    @Column
    private double neutral;

    @Column
    private double happy;

    @Column
    private LocalDateTime createdDate;
}
