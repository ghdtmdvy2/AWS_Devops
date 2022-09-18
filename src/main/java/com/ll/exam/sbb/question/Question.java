package com.ll.exam.sbb.question;

import com.ll.exam.sbb.BaseTimeEntity;
import com.ll.exam.sbb.answer.Answer;
import com.ll.exam.sbb.emotion.Emotion;
import com.ll.exam.sbb.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
public class Question extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    @Column(length = 200) // varchar(200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;


    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL})
    private List<Emotion> emotionList = new ArrayList<>();

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;


    private Integer hitCount = 0;


    public void addEmotion(Emotion emotion) {
        emotion.setQuestion(this);
        getEmotionList().add(emotion);
    }
}
