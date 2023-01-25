package com.ll.exam.sbb.service;

import com.ll.exam.sbb.user.entity.SiteUser;
import com.ll.exam.sbb.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Test
    public void user_signup(){
        String username = "test";
        String email = "test3@test.com";
        String password = "1234";
        userService.create(username,email,password);

        SiteUser user = userService.getUser(username);
        assertThat(user.getUsername()).isNotNull();
        assertThat(user.getEmail()).isNotNull();
        assertThat(passwordEncoder.matches(password, user.getPassword())).isTrue();
    }

    @Test
    public void user_information_update(){

        String oldPassword = "1234";
        String changePassword = "4321";
        SiteUser user = userService.getUser("user1");

        assertThat(passwordEncoder.matches(oldPassword,user.getPassword()));

        userService.update(user,changePassword);

        assertThat(user.getUsername()).isNotNull();
        assertThat(user.getEmail()).isNotNull();
        assertThat(passwordEncoder.matches(oldPassword, user.getPassword())).isFalse();
        assertThat(passwordEncoder.matches(changePassword, user.getPassword())).isTrue();
    }
}