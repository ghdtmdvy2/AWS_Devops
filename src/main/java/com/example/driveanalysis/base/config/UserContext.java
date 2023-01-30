package com.example.driveanalysis.base.config;

import com.example.driveanalysis.user.entity.SiteUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserContext extends User {
    private final Long id;
    private final String email;
    private final String username;
    private boolean isProductPaid;

    private long restCash;

    public UserContext(SiteUser users, List<GrantedAuthority> authorities) {
        super(users.getUsername(), users.getPassword(), authorities);
        this.id = users.getId();
        this.email = users.getEmail();
        this.username = users.getUsername();
    }

    public SiteUser getUser() {
        SiteUser user = new SiteUser();
        user.setRestCash(restCash);
        user.setProductPaid(isProductPaid);
        user.setEmail(email);
        user.setUsername(username);
        user.setId(id);
        return user;
    }
}
