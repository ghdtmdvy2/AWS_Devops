package com.example.driveanalysis.emotion.entity;

import com.example.driveanalysis.analysis.entity.Analysis;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Analysis analysis;

    @ManyToOne(fetch = FetchType.LAZY)
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
