package com.example.driveanalysis.order.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ProductOrder extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @ManyToOne
    private SiteUser orderer;

    private LocalDateTime payDate; // 결제날짜
    private boolean readyStatus; // 주문완료여부
    private boolean isPaid; // 결제완료여부
    private boolean isCanceled; // 취소여부
    private boolean isRefunded; // 환불여부

    @OneToMany(mappedBy = "productOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public int calculatePay(){
        int price = 0;
        for (OrderItem orderItem : orderItems) {
            price += orderItem.getPrice() * orderItem.getAmount();
        }
        return price;
    }

    public void setPaymentDone(){
        for (OrderItem orderItem : orderItems) {
            orderItem.setPaymentDone();
        }
        this.setPayDate(LocalDateTime.now());
        this.setPaid(true);
    }
}