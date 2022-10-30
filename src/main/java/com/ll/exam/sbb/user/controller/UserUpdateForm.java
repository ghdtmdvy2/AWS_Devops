package com.ll.exam.sbb.user.controller;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserUpdateForm {
    @Column
    private String oldPassword;
    @Column
    private String password;
    @Column
    private String confirmPassword;
}
