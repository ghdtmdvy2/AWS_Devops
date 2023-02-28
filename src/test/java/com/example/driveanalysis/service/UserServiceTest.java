package com.example.driveanalysis.service;

import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Test
    void user_signup(){
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
    void user_information_update(){

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