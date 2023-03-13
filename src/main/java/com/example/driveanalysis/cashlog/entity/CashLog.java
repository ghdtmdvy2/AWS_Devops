package com.example.driveanalysis.cashlog.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
public class CashLog extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    @ManyToOne
    @ToString.Exclude
    private SiteUser user;
    private long price;
    private String eventType;
}
