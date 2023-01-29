package com.example.driveanalysis.order.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.product.entity.Product;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OrderItem extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @ManyToOne
    private ProductOrder productOrder;

    private LocalDateTime payDate;

    @ManyToOne
    private Product product;

    // 가격
    private int price; // 권장판매가
    private int salePrice; // 실제판매가
    private int wholesalePrice; // 도매가
    private int pgFee; // 결제대행사 수수료
    private int payPrice; // 결제금액
    private int refundPrice; // 환불금액
    private boolean isPaid; // 결제여부
}
