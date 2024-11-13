package com.example.noticeweb.service;

import com.example.noticeweb.dto.ArticleForm;
import com.example.noticeweb.entity.Article;
import com.example.noticeweb.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();

    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
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
