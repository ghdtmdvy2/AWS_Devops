package com.ll.exam.sbb.aritlce.entity;

import com.ll.exam.sbb.answer.entity.Answer;
import com.ll.exam.sbb.base.config.BaseTimeEntity;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Article extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    @Column(length = 200) // varchar(200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.ALL})
    private List<Answer> answerList = new ArrayList<>();

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;


    private Integer hitCount = 0;


    public void addAnswer(Answer answer) {
        answer.setArticle(this);
        getAnswerList().add(answer);
    }
}
