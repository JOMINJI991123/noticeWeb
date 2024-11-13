package com.example.noticeweb.service;

import com.example.noticeweb.dto.CommentDto;
import com.example.noticeweb.entity.Article;
import com.example.noticeweb.entity.Comment;
import com.example.noticeweb.repository.ArticleRepository;
import com.example.noticeweb.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;


    public List<CommentDto> comments(Long articleId) {

        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 대상게시글 존재여부확인
        Article article= articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("대상 게시글이 없습니다."));

        Comment comment = Comment.createComment(dto, article);

        Comment created = commentRepository.save(comment);

        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));

        target.patch(dto);

        Comment updated = commentRepository.save(target);

        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제 대상이 없습니다."));
        commentRepository.delete(target);

        return CommentDto.createCommentDto(target);
    }
}
