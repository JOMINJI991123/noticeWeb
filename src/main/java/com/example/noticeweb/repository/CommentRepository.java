package com.example.noticeweb.repository;

import com.example.noticeweb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query(value =
            "Select * FROM comment WHERE article_id=:articleId", nativeQuery = true)
    List<Comment> findByArticleId(@Param("articleId")Long articleId);

    List<Comment> findByNickname(@Param("nickname") String nickname);
}
