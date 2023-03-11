package com.example.driveanalysis.aritlce.entity;

import com.example.driveanalysis.answer.entity.Answer;
import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode
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

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Article)) {
            return false;
        }

        Article other = (Article) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.subject,other.subject)
                && Objects.equals(this.content,other.content)
                && Objects.equals(this.hitCount,other.hitCount);
    }
}
