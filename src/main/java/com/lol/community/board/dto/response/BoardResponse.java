package com.lol.community.board.dto.response;

import com.lol.community.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private Integer id;
    private String boardType;
    private  Integer userId;
    private String writer;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createdAt;

    public BoardResponse(Board board) {
        id = board.getId();
        userId = board.getUser().getId();
        writer = board.getUser().getName();
        title = board.getTitle();
        viewCount = board.getViewCount();
        likeCount = board.getLikeCount();
        createdAt = board.getCreatedAt();
    }
}
