package com.example.noticeweb.repository;

import com.example.noticeweb.entity.Article;
import com.example.noticeweb.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        Long articleId = 4L;

        List<Comment> comments = commentRepository.findByArticleId(articleId);

        Article article = new Article(4L,"hello","world");
        Comment a= new Comment(1L,article,"hello1","first");
        Comment b= new Comment (2L,article,"hello2","second");
        Comment c= new Comment(3L,article,"hello3","third");

        List<Comment> expected = Arrays.asList(a,b,c);

        assertEquals(comments.toString(),expected.toString(),"4번글의 댓글" );

    }

    @Test
    @DisplayName("닉네임으로 게시물들 찾기")
    void findByNickname() {
        String nickname = "rrr";

        List<Comment> comments = commentRepository.findByNickname(nickname);

        Article article = new Article(4L,"영화뭐","rrr");
        Comment a= new Comment(1L,article,"rrr","라라랜드");

        List<Comment> expected = Arrays.asList(a);

        assertEquals(comments.toString(),expected.toString(),"닉네임 rrr의 댓글들");

    }

}