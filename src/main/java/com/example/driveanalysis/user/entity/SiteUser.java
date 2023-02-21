package com.example.driveanalysis.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SiteUser {

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
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SiteUser)) {
            return false;
        }

        SiteUser other = (SiteUser) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.username,other.username)
                && Objects.equals(this.email,other.email)
                && Objects.equals(this.isProductPaid,other.isProductPaid);
    }
}
