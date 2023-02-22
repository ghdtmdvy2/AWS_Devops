package com.example.driveanalysis.aritlce.controller;

import com.example.driveanalysis.answer.dto.AnswerForm;
import com.example.driveanalysis.aritlce.dto.ArticleForm;
import com.example.driveanalysis.aritlce.service.ArticleService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import com.example.driveanalysis.aritlce.entity.Article;
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

@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {
    // @Autowired // 필드 주입
    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/")
    public String list(@RequestParam(defaultValue = "") String kw, @RequestParam(defaultValue = "") String sortCode, Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Article> paging = articleService.getList(kw, page, sortCode);

        model.addAttribute("paging", paging);

        return "article/article_list";
    }

    @GetMapping("/{articleId}")
    public String detail(Model model, @PathVariable long articleId, AnswerForm answerForm) {
        Article article = articleService.getArticle(articleId);
        articleService.articleIncreaseHitCount(article);
        model.addAttribute("article", article);

        return "article/article_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{articleId}",params = "redirectURL")
    public String articleModify(ArticleForm articleForm, @PathVariable("articleId") long articleId, Principal principal, @RequestParam("redirectURL") String redirectURL) {
        Article article = this.articleService.getArticle(articleId);

        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());

        return "article/article_form";
    }

    @PatchMapping("/{articleId}")
    public String articleModify(@Valid ArticleForm articleForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("articleId") long articleId) {
        if (bindingResult.hasErrors()) {
            return "article/article_form";
        }

        Article article = this.articleService.getArticle(articleId);

        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent());
        return "redirect:/article/%d".formatted(articleId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/", params = "redirectURL")
    public String articleCreate(ArticleForm articleForm) {
        return "article/article_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public String articleCreate(Principal principal, Model model, @Valid ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/article_create";
        }

        SiteUser siteUser = userService.getUser(principal.getName());

        articleService.create(articleForm.getSubject(), articleForm.getContent(), siteUser);
        return "redirect:/article/"; // 질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{articleId}")
    public String articleDelete(Principal principal, @PathVariable("articleId") long articleId) {
        Article article = articleService.getArticle(articleId);

        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        articleService.delete(article);

        return "redirect:/article/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{articleId}")
    public String articleVote(Principal principal, @PathVariable("articleId") long articleId) {
        Article article = articleService.getArticle(articleId);
        SiteUser siteUser = userService.getUser(principal.getName());
        articleService.vote(article, siteUser);
        return "redirect:/article/%d".formatted(articleId);
    }
}
