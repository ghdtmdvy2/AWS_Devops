package com.example.driveanalysis.user.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class SiteUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
    private boolean isProductPaid;
    public SiteUser(long id) {
        this.id = id;
    }

}
