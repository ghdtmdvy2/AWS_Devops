package com.ll.exam.sbb.analysis.entity;

import com.ll.exam.sbb.base.config.BaseTimeEntity;
import com.ll.exam.sbb.emotion.entity.Emotion;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
public class Analysis extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @OneToMany(mappedBy = "analysis", cascade = {CascadeType.ALL})
    private List<Emotion> emotionList = new ArrayList<>();

    @ManyToOne
    private SiteUser author;

}
