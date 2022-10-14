package com.ll.exam.sbb.analysis.controller;

import com.ll.exam.sbb.analysis.dto.AnalysisForm;
import com.ll.exam.sbb.analysis.entity.Analysis;
import com.ll.exam.sbb.analysis.service.AnalysisService;
import com.ll.exam.sbb.answer.dto.AnswerForm;
import com.ll.exam.sbb.user.entity.SiteUser;
import com.ll.exam.sbb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/analysis")
@Controller
@RequiredArgsConstructor // 생성자 주입
// 컨트롤러는 Repository가 있는지 몰라야 한다.
// 서비스는 웹브라우저라는것이 이 세상에 존재하는지 몰라야 한다.
// 리포지터리는 서비스를 몰라야 한다.
// 서비스는 컨트롤러를 몰라야 한다.
// DB는 리포지터리를 몰라야 한다.
// SPRING DATA JPA는 MySQL을 몰라야 한다.
// SPRING DATA JPA(리포지터리) -> JPA -> 하이버네이트 -> JDBC -> MySQL Driver -> MySQL
public class AnalysisController {
    // @Autowired // 필드 주입
    private final AnalysisService analysisService;
    private final UserService userService;

    @GetMapping("/list/{id}")
    // 이 자리에 @ResponseBody가 없으면 resources/question_list/question_list.html 파일을 뷰로 삼는다.
    public String list(@RequestParam(defaultValue = "") String kw, @RequestParam(defaultValue = "") String sortCode, Model model, @RequestParam(defaultValue = "0") int page,@PathVariable long id) {
        Page<Analysis> paging = analysisService.getList(kw, page, sortCode, id);

        // 미래에 실행된 question_list.html 에서
        // questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
        model.addAttribute("paging", paging);

        return "question/question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable long id, AnswerForm answerForm) {
        Analysis analysis = analysisService.getAnalysis(id);

        model.addAttribute("question", analysis);

        return "question/question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String analysisModify(AnalysisForm analysisForm, @PathVariable("id") Integer id, Principal principal) {
        Analysis analysis = this.analysisService.getAnalysis(id);

        if (!analysis.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        analysisForm.setSubject(analysis.getSubject());
        analysisForm.setContent(analysis.getContent());

        return "question/question_form";
    }

    @PostMapping("/modify/{id}")
    public String analysisModify(@Valid AnalysisForm analysisForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }

        Analysis analysis = this.analysisService.getAnalysis(id);

        if (!analysis.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.analysisService.modify(analysis, analysisForm.getSubject(), analysisForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String analysisCreate(AnalysisForm analysisForm) {
        return "/question/question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String analysisCreate(Principal principal, Model model, @Valid AnalysisForm analysisForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }

        SiteUser siteUser = userService.getUser(principal.getName());

        analysisService.create(analysisForm.getSubject(), analysisForm.getContent(), siteUser);
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String analysisDelete(Principal principal, @PathVariable("id") Integer id) {
        Analysis analysis = analysisService.getAnalysis(id);

        if (!analysis.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        analysisService.delete(analysis);

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Long id) {
        Analysis analysis = analysisService.getAnalysis(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        analysisService.vote(analysis, siteUser);
        return "redirect:/question/detail/%d".formatted(id);
    }
}
