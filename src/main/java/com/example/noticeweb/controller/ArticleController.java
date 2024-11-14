package com.example.noticeweb.controller;

import com.example.noticeweb.dto.ArticleForm;
import com.example.noticeweb.dto.CommentDto;
import com.example.noticeweb.entity.Article;
import com.example.noticeweb.repository.ArticleRepository;
import com.example.noticeweb.service.ArticleService;
import com.example.noticeweb.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    // 새 게시글 작성 페이지 로드
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    // 새 게시글 작성
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        Article createdArticle = articleService.create(form);
        return "redirect:/articles";
    }

    // 게시글 상세페이지 로드
    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        Map<String, Object> response = articleService.show(id);
        model.addAttribute("commentDtos",response.get("commentDtos"));
        model.addAttribute("article", response.get("articleEntity"));
        return "articles/show";
    }

    // 게시판 모든글 조회
    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articleEntityList = articleService.index();
        log.info(articleEntityList.toString());
        model.addAttribute("articleList",articleEntityList);
        return "articles/index";
    }

    // 게시글 수정 페이지 로드
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article",articleEntity);
        return "articles/edit";
    }

    @Transactional
    // 게시글 수정 요청
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        Article updatedArticle = articleService.update(form);
        return "redirect:/articles/"+ updatedArticle.getId();
    }

    // 게시글 삭제 요청
    @Transactional
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes rttr) {
        Article deletedArticle = articleService.delete(id);
        rttr.addFlashAttribute("msg","삭제완료");
        return "redirect:/articles";
    }
}
