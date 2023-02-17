package com.example.driveanalysis.aritlce.entity;

import com.example.driveanalysis.answer.entity.Answer;
import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;
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

    @OneToMany(mappedBy = "article", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Answer> answerList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;

    private Integer hitCount;


    public void addAnswer(Answer answer) {
        answer.setArticle(this);
        getAnswerList().add(answer);
    }

    public String getJdenticon() {
        return "article__" + getId();
    }
}
