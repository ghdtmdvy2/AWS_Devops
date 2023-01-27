package com.ll.exam.sbb.product.entity;

import com.ll.exam.sbb.base.config.BaseTimeEntity;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String content;
    private int price;
    @ManyToOne
    private SiteUser author;
}
