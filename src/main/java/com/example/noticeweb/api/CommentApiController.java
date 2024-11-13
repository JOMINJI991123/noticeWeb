package com.example.noticeweb.api;

import com.example.noticeweb.dto.CommentDto;
import com.example.noticeweb.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable("articleId") Long articleId) {
        List<CommentDto> dtos = commentService.comments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable("articleId") Long articleId,
                                             @RequestBody CommentDto dto) {

        CommentDto createDto = commentService.create(articleId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createDto);

    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable("id") Long id,
                                             @RequestBody CommentDto dto) {
        log.info("id = {}, articleId = {}, nickname = {}, body= {}",dto.getId(),dto.getArticleId(),dto.getNickname(),dto.getBody());
        CommentDto updateDto = commentService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updateDto);


    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable("id") Long id) {
        CommentDto deleteDto = commentService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);


    }

}