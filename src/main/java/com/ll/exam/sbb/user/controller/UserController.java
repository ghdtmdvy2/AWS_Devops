package com.ll.exam.sbb.user.controller;

import com.ll.exam.sbb.base.config.UserContext;
import com.ll.exam.sbb.base.exception.SignupEmailDuplicatedException;
import com.ll.exam.sbb.base.exception.SignupUsernameDuplicatedException;
import com.ll.exam.sbb.emotion.entity.Emotion;
import com.ll.exam.sbb.emotion.service.EmotionService;
import com.ll.exam.sbb.user.dto.UserCreateForm;
import com.ll.exam.sbb.user.entity.SiteUser;
import com.ll.exam.sbb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
import java.security.Principal;
import java.util.ArrayList;
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

        List<Emotion> users_emotions = emotionService.findByAuthor_id(userContext.getId(),yearMonth);
        List<Emotion> other_users_emotions = emotionService.findAll(yearMonth);

        double users_angry = 0;
        double users_happy = 0;
        double users_neutral = 0;

        double other_users_angry = 0;
        double other_users_happy = 0;
        double other_users_neutral = 0;
        for (Emotion emotion : users_emotions){
            users_angry += emotion.getAngry();
            users_happy += emotion.getHappy();
            users_neutral += emotion.getNeutral();
        }

        for (Emotion other_users_emotion : other_users_emotions){
            other_users_angry += other_users_emotion.getAngry();
            other_users_happy += other_users_emotion.getHappy();
            other_users_neutral += other_users_emotion.getNeutral();
        }
        users_angry = users_angry/(users_emotions.size());
        users_happy = users_happy/(users_emotions.size());
        users_neutral = users_neutral/(users_emotions.size());

        users_angry = Math.round(users_angry * 10) / 10.0;
        users_happy = Math.round(users_happy * 10) / 10.0;
        users_neutral = Math.round(users_neutral * 10) / 10.0;

        other_users_angry = other_users_angry/(other_users_emotions.size());
        other_users_happy = other_users_happy/(other_users_emotions.size());
        other_users_neutral = other_users_neutral/(other_users_emotions.size());

        other_users_angry = Math.round(other_users_angry * 10) / 10.0;
        other_users_happy = Math.round(other_users_happy * 10) / 10.0;
        other_users_neutral = Math.round(other_users_neutral * 10) / 10.0;

        double diff_emotion_angry = other_users_angry - users_angry;
        double diff_emotion_happy = other_users_happy - users_happy;
        double diff_emotion_neutral = other_users_neutral - users_neutral;

        diff_emotion_angry = Math.round(diff_emotion_angry * 10) / 10.0 > 0 ? Math.round(diff_emotion_angry * 10) / 10.0 : Math.round(diff_emotion_angry * 10) / 10.0 * (-1.0);
        diff_emotion_happy = Math.round(diff_emotion_happy * 10) / 10.0 > 0 ? Math.round(diff_emotion_happy * 10) / 10.0 : Math.round(diff_emotion_happy * 10) / 10.0 * (-1.0);
        diff_emotion_neutral = Math.round(diff_emotion_neutral * 10) / 10.0 > 0 ? Math.round(diff_emotion_neutral * 10) / 10.0 : Math.round(diff_emotion_neutral * 10) / 10.0 * (-1.0);

        model.addAttribute("users_angry",users_angry);
        model.addAttribute("users_happy",users_happy);
        model.addAttribute("users_neutral",users_neutral);

        model.addAttribute("other_users_angry",other_users_angry);
        model.addAttribute("other_users_happy",other_users_happy);
        model.addAttribute("other_users_neutral",other_users_neutral);

        model.addAttribute("diff_emotion_angry",diff_emotion_angry);
        model.addAttribute("diff_emotion_happy",diff_emotion_happy);
        model.addAttribute("diff_emotion_neutral",diff_emotion_neutral);
        return "user/information";
    }
}
