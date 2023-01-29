package com.example.driveanalysis.order.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
