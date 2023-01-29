package com.example.driveanalysis.order.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProductOrder extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @ManyToOne
    private SiteUser orderer;

    private LocalDateTime payDate;
    private boolean readyStatus;
    private boolean isPaid;
    private boolean isCanceled;
    private boolean isRefunded;
}
