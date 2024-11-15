package com.example.noticeweb.repository;

import com.example.noticeweb.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {


    @Override
    ArrayList<Article> findAll();
}
