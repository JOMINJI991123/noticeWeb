package com.example.noticeweb.entity;

import com.example.noticeweb.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    @Column
    private String nickname;
    @Column
    private String body;


    public static Comment createComment(CommentDto dto, Article article) {

        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글의 작성 대상 게시글의 아이디와 현재 게시글의 아이디가 다릅니다..");

        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }


    public void patch(CommentDto dto) {
        if (this.id != dto.getId()){
            throw new IllegalArgumentException("잘못된 id입력");
        }
        if (dto.getNickname() != null){
            this.nickname = dto.getNickname();
        }
        if (dto.getBody() != null){
            this.body = dto.getBody();
        }
    }
}
