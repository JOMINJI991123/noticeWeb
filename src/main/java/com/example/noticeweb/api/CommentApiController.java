package com.example.noticeweb.api;

import com.example.noticeweb.dto.CommentDto;
import com.example.noticeweb.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    //해당 게시글의 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable("articleId") Long articleId) {
        List<CommentDto> dtos = commentService.comments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 해당 게시글의 댓글 추가
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable("articleId") Long articleId,
                                             @RequestBody CommentDto dto) {

        CommentDto createDto = commentService.create(articleId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(createDto);

    }

    // 댓글 수정
    @Transactional
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable("id") Long id,
                                             @RequestBody CommentDto dto) {
        CommentDto updateDto = commentService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // 댓글 삭제
    @Transactional
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable("id") Long id) {
        CommentDto deleteDto = commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);


    }

}