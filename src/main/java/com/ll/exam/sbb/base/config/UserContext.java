package com.ll.exam.sbb.base.config;

import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserContext extends User {
    private final Long id;
    private final String email;
    private final String username;

    public UserContext(SiteUser users, List<GrantedAuthority> authorities) {
        super(users.getUsername(), users.getPassword(), authorities);
        this.id = users.getId();
        this.email = users.getEmail();
        this.username = users.getUsername();
    }
}
