package com.example.noticeweb.dto;

import com.example.noticeweb.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class ArticleForm {
    private String title;
    private String content;

    public Article toEntity() {

        return new Article (null, title, content);
    }
}
