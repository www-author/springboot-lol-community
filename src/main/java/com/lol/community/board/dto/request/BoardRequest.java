package com.lol.community.board.dto.request;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {
    private Integer userId;
    private Integer categoryId;
    private BoardType boardType;
    private String title;
    private String content;

    public Board toEntity() {
        return Board.builder()
                .userId(this.userId)
                .categoryId(this.categoryId)
                .boardType(this.boardType.name())
                .title(this.title)
                .content(this.content)
                .build();
    }
}
