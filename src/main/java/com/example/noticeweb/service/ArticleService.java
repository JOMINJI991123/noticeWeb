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

    // 게시판 모든글 조회
    public List<Article> index() {
        return articleRepository.findAll();
    }

    // 게시글 상세페이지
    public Map<String, Object> show(Long id) {
        Map<String, Object> response = new HashMap<>();

        // 조회수기능
        Article articleEntity = articleRepository.findById(id).orElse(null);
        if (articleEntity != null) {
            articleEntity.setViews(articleEntity.getViews() + 1);
            articleRepository.save(articleEntity);
            response.put("articleEntity", articleEntity);
        }

        List<CommentDto> commentDtos = commentService.comments(id);
        response.put("commentDtos", commentDtos);
        log.info(response.toString());
        return response;
    }

    // 게시글 생성
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);

    }

    // 게시글 수정 요청
    public Article update(ArticleForm dto) {
        Article article = dto.toEntity();
        Article target = articleRepository.findById(article.getId()).orElse(null);

        if (target == null) {
            return null;
        }
        target.patch(article);

        return articleRepository.save(target);
    }

    // 게시글 삭제 요청
    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

}