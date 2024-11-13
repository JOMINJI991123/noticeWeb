package com.example.noticeweb.controller;

import com.example.noticeweb.dto.ArticleForm;
import com.example.noticeweb.dto.CommentDto;
import com.example.noticeweb.entity.Article;
import com.example.noticeweb.repository.ArticleRepository;
import com.example.noticeweb.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        Article article = form.toEntity();
        log.info(article.toString());
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        log.info("id="+id);

        Article articeEntity = articleRepository.findById(id).orElse(null);

        List<CommentDto> commentDtos = commentService.comments(id);

        model.addAttribute("commentDtos",commentDtos);
        log.info(articeEntity.toString());
        model.addAttribute("article", articeEntity);


        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articleEntityList = articleRepository.findAll();
        model.addAttribute("articleList",articleEntityList);
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article",articleEntity);
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        Article articleEntity = form.toEntity();
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if (target!=null){
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/"+ articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes rttr) {
        Article target = articleRepository.findById(id).orElse(null);

        if(target != null){
            articleRepository.delete(target);
        }

        rttr.addFlashAttribute("msg","삭제완료");
        return "redirect:/articles";
    }
}
