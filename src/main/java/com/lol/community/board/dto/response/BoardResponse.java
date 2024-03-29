package com.lol.community.board.dto.response;

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
    private Long id;
    private Long categoryId;
    private Long userId;
    private String boardType;
    private String title;
    private String content;
    private Boolean isDeleted;
    private Integer viewCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
