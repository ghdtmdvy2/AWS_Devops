package com.example.driveanalysis.analysis.entity;

import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.emotion.entity.Emotion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Analysis extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @OneToMany(mappedBy = "analysis", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Emotion> emotionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser author;

}
