package com.example.driveanalysis.product.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String content;
    private int price;
    private boolean isOrderable;
    private int stock;
    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser author;

    public String getJdenticon() {
        return "product__" + getId();
    }

}
