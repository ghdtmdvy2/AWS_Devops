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
    public String list(@RequestParam(defaultValue = "") String kw, @RequestParam(defaultValue = "") String sortCode, Model model, @RequestParam(defaultValue = "0") int page,@PathVariable long id) {
        Page<Analysis> paging = analysisService.getList(kw, page, sortCode, id);

        model.addAttribute("paging", paging);

        return "analysis/analysis_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable long id, AnswerForm answerForm) {
        Analysis analysis = analysisService.getAnalysis(id);

        model.addAttribute("analysis", analysis);

        return "analysis/analysis_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String analysisDelete(Principal principal, @PathVariable("id") Integer id) {
        Analysis analysis = analysisService.getAnalysis(id);

        if (!analysis.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        analysisService.delete(analysis);

        return "redirect:/analysis/list/%d".formatted(analysis.getAuthor().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String analysisVote(Principal principal, @PathVariable("id") Long id) {
        Analysis analysis = analysisService.getAnalysis(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        analysisService.vote(analysis, siteUser);
        return "redirect:/analysis/detail/%d".formatted(id);
    }
}
