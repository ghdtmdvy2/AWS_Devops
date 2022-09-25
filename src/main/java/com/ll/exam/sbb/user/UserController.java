package com.ll.exam.sbb.user;

import com.ll.exam.sbb.emotion.Emotion;
import com.ll.exam.sbb.emotion.EmotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final EmotionService emotionService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "signup_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/information")
    public String information(@AuthenticationPrincipal UserContext userContext, Model model){
        ArrayList<Emotion> emotions = emotionService.findByAuthor_id(userContext.getId());
        double angry = 0;
        double happy = 0;
        double neutral = 0;
        for (Emotion emotion : emotions){
            angry += emotion.getAngry();
            happy += emotion.getHappy();
            neutral += emotion.getNeutral();
        }
        angry = angry/(emotions.size());
        happy = happy/(emotions.size());
        neutral = neutral/(emotions.size());
        model.addAttribute("angry",angry);
        model.addAttribute("happy",happy);
        model.addAttribute("neutral",neutral);
        return "user_information";
    }
}
