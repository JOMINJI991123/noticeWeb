package com.example.noticeweb.service;

import com.example.noticeweb.dto.ArticleForm;
import com.example.noticeweb.entity.Article;
import com.example.noticeweb.entity.Comment;
import com.example.noticeweb.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    // article create test
    @Test
    void createArticleTest(){
        Article createdArticle = articleService.create(new ArticleForm(0l,"영화추천","타이타닉",null,null));
        Article target = articleRepository.findById(createdArticle.getId()).orElse(null);

        assertNotNull(target);
        assertNotNull(target.getCreatedDate());
        assertEquals(target.getViews(),0);
        assertEquals(target.getTitle(),"영화추천");
        assertEquals(target.getContent(),"타이타닉");

    }

    @Test
    void updateArticleTest(){
        Article createdArticle = articleService.create(new ArticleForm(0l,"영화추천","타이타닉",null,null));
        Article updatedArticle = articleService.update(new ArticleForm(createdArticle.getId(), "영화","파일럿",null,null));

        assertNotNull(updatedArticle);
        assertEquals(updatedArticle.getTitle(),"영화");
        assertEquals(updatedArticle.getContent(),"파일럿");
    }

    @Test
    void readArticleTest(){
        Article createdArticle = articleService.create(new ArticleForm(0l,"영화추천","타이타닉",null,null));
        List<Article> readArticles = articleService.index();

        assertEquals(createdArticle.getTitle(), readArticles.get(readArticles.size()-1).getTitle());
        assertEquals(createdArticle.getContent(), readArticles.get(readArticles.size()-1).getContent());

    }

    @Test
    void deleteArticleTest(){
        Article createdArticle = articleService.create(new ArticleForm(0l,"영화추천","타이타닉",null,null));
        Article deletedArticle = articleService.delete(createdArticle.getId());

        assertTrue(articleRepository.findById(deletedArticle.getId()).isEmpty());
    }
}