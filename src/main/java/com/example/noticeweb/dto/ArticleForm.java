package com.example.noticeweb.dto;

import com.example.noticeweb.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class ArticleForm {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Long views;

    public Article toEntity() {

        return new Article (null, title, content,null,0l);
    }
}
