package com.example.driveanalysis.userhistory.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
public class UserHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private SiteUser siteUser;

    private String username;

    private String password;

    private String email;

    private boolean isProductPaid;
}
