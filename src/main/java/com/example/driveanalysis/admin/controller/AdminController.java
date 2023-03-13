package com.example.driveanalysis.admin.controller;

import com.example.driveanalysis.user.dto.UserUpdateForm;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    @GetMapping("/userInformation")
    public String userInformationUpdate(Model model){
        return "admin/userInformation";
    }
    @PatchMapping("/userInformation")
    public String userInformationUpdate(@RequestParam String password, @RequestParam String username){
        SiteUser user = userService.getUser(username);
        userService.update(user,password);
        return "redirect:/";
    }
}
