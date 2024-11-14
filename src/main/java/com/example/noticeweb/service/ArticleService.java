package com.example.noticeweb.service;
import com.example.noticeweb.dto.ArticleForm;
import com.example.noticeweb.dto.CommentDto;
import com.example.noticeweb.entity.Article;
import com.example.noticeweb.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    public List<Article> index() {
        return articleRepository.findAll();

    }

    public Map<String,Object> show(Long id) {
        Map<String, Object> response = new HashMap<>();

        // 조회수기능
        Article articleEntity = articleRepository.findById(id).orElse(null);
        if(articleEntity!=null){
            articleEntity.setViews(articleEntity.getViews()+1);
            articleRepository.save(articleEntity);
            response.put("articleEntity",articleEntity);
        }

        List<CommentDto> commentDtos = commentService.comments(id);
        response.put("commentDtos",commentDtos);
        log.info(response.toString());
        return response;
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();

        Article target = articleRepository.findById(article.getId()).orElse(null);

        if(target != null){
            return null;
        }
        return articleRepository.save(article);

    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);

        if(target == null || article.getId() != id){
            return null;
        }
        target.patch(article);
        Article updated= articleRepository.save(target);

        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if(target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        articleRepository.findById(-1L).orElseThrow(
                ()-> new IllegalArgumentException("결재실패")
        );

        return articleList;
    }
}
