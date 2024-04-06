package com.lol.community.board.controller;

import com.lol.community.board.domain.Board;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/board")
@Tag(name = "게시판 CRUD")
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    // TODO 조회
    // TODO [공통] 스웨거 정의
    // TODO [공통] 게시판 파일을 추가한 CRUD

    @PostMapping("/write")
    @Operation(summary = "게시글 등록", description = "게시판의 게시글 생성")
    @ApiResponse(responseCode = "201", description = "요청 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<BoardResponse> addArticle(@RequestBody BoardRequest request) {
        BoardResponse response = boardService.save(request)
                .toResponse();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정", description = "게시글 ID 기반의 특정 게시글 수정")
    public ResponseEntity<BoardResponse> updateArticle(
            @PathVariable("id") Long id,
            @RequestBody BoardRequest request
    ) {
        Board board = boardService.modify(id, request);
        return ResponseEntity.ok(board.toResponse());
    }
}
