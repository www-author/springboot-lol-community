package com.lol.community.board.controller;

import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@Tag(name = "게시판 CRUD")
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<BoardResponse> addArticle(@RequestBody BoardRequest request) {
        BoardResponse response = boardService.save(request)
                .toResponse();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
