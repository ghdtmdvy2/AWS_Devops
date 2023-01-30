package com.example.driveanalysis.user.controller;

import com.example.driveanalysis.base.config.UserContext;
import com.example.driveanalysis.base.util.Ut;
import com.example.driveanalysis.user.dto.UserUpdateForm;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.base.exception.SignupEmailDuplicatedException;
import com.example.driveanalysis.base.exception.SignupUsernameDuplicatedException;
import com.example.driveanalysis.emotion.entity.Emotion;
import com.example.driveanalysis.emotion.service.EmotionService;
import com.example.driveanalysis.user.dto.UserCreateForm;
import com.example.driveanalysis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final EmotionService emotionService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "user/signup_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "user/signup_form";
        }

        return "redirect:/";
    }
    @GetMapping("/information/update")
    @PreAuthorize("isAuthenticated()")
    String show_information_update(Model model, UserUpdateForm userUpdateForm, @AuthenticationPrincipal UserDetails userContext){
        SiteUser users = userService.getUser(userContext.getUsername());

        model.addAttribute("users",users);
        return "user/information_update";
    }

    @PostMapping("/information/update")
    @PreAuthorize("isAuthenticated()")
    String information_update(Model model, @Valid UserUpdateForm userUpdateForm, BindingResult bindingResult, @AuthenticationPrincipal UserContext userContext, HttpSession session){
        SiteUser users = userService.getUser(userContext.getUsername());
        if (bindingResult.hasErrors()) {
            model.addAttribute("users",users);
            return "user/information_update";
        }
        if (!passwordEncoder.matches(userUpdateForm.getOldPassword(),users.getPassword())){
            bindingResult.reject("oldPassword", "현재 비밀번호가 일치하지 않습니다.");
            model.addAttribute("users",users);
            return "user/information_update";
        }
        if (!userUpdateForm.getConfirmPassword().equals(userUpdateForm.getPassword())){
            bindingResult.reject("password", "두 비밀번호가 일치하지 않습니다.");
            model.addAttribute("users",users);
            return "user/information_update";
        }
        userService.update(users,userUpdateForm.getPassword());
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login_form";
    }

    @GetMapping("/information")
    public String information(@AuthenticationPrincipal UserContext userContext, Model model, @RequestParam(defaultValue = "")String yearMonth){

        List<Emotion> users_emotions = emotionService.currentUserFindEmotions(userContext.getId(),yearMonth);
        List<Emotion> other_users_emotions = emotionService.AllUsersFindEmotions(yearMonth);

        double[] currentUserEmotionAvg = Ut.emotionsAvgCreate(users_emotions);
        double[] otherUserEmotionAvg = Ut.emotionsAvgCreate(other_users_emotions);
        double[] diffUserEmotionAvg = Ut.diffAvgEmotions(currentUserEmotionAvg,otherUserEmotionAvg);


        model.addAttribute("users_angry",currentUserEmotionAvg[0]);
        model.addAttribute("users_happy",currentUserEmotionAvg[1]);
        model.addAttribute("users_neutral",currentUserEmotionAvg[2]);

        model.addAttribute("other_users_angry",otherUserEmotionAvg[0]);
        model.addAttribute("other_users_happy",otherUserEmotionAvg[1]);
        model.addAttribute("other_users_neutral",otherUserEmotionAvg[2]);

        model.addAttribute("diff_emotion_angry",diffUserEmotionAvg[0]);
        model.addAttribute("diff_emotion_happy",diffUserEmotionAvg[1]);
        model.addAttribute("diff_emotion_neutral",diffUserEmotionAvg[2]);

        return "user/information";
    }
}
