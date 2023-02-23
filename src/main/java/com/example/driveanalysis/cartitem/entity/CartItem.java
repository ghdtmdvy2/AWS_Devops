package com.example.driveanalysis.cartitem.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class CartItem extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    // 한 유저의 장바구니가 담는 게 여러개
    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser buyer;

    // 장바구니 하나에 상품 여러개
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int amount;
}
