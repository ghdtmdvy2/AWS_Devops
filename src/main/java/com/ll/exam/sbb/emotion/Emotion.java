package com.ll.exam.sbb.emotion;

import com.ll.exam.sbb.BaseTimeEntity;
import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Emotion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    @Column
    private double angry = 24.4;

    @Column
    private double neutral = 25.5;

    @Column
    private double happy = 50.1;
}
