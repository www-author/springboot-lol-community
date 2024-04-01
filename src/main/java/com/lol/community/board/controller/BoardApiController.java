package com.lol.community.board.controller;

import com.lol.community.board.domain.Board;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.response.BoardBaseResponse;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.service.BoardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
@Tag(name = "게시판 CRUD")
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardServiceImpl boardService;

    // TODO 조회
    // TODO [공통] 스웨거 정의
    // TODO [공통] 게시판 파일을 추가한 CRUD

    @PostMapping("/article")
    /*@PreAuthorize("isAuthenticated()") TODO : 인가된 사용자만*/
    @Operation(summary = "게시글 등록", description = "게시판의 게시글 생성0")
    @ApiResponse(responseCode = "201", description = "요청 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<BoardResponse> addArticle(@RequestBody BoardRequest request) {
        // TODO 현재 로그인된 사용자 이메일 정보 가져오기
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();*/
        // TODO 로그인 연동 전까지 임시 하드 코딩
        Board board = boardService.save(request, "admin@gmail.com");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BoardResponse(board));
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정", description = "게시글 ID 기반의 특정 게시글 수정")
    public ResponseEntity<BoardBaseResponse> updateArticle(
            @PathVariable("id") Integer id,
            @RequestBody BoardRequest request
    ) {
        Board board = boardService.modify(id, request);
        return ResponseEntity.ok(board.toResponse());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제", description = "게시글 ID 기반의 게시글 삭제")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        boardService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
