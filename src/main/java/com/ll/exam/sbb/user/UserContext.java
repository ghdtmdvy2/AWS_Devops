package com.ll.exam.sbb.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
