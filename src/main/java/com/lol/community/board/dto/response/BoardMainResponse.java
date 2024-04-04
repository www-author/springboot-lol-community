package com.lol.community.board.dto.response;

import com.lol.community.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardMainResponse implements BoardMainView {
    private Long totalCount;
    private Integer boardId;
    private String writer;
    private String title;
    private String content;

    public BoardMainResponse(Board board) {
        totalCount = Long.valueOf(board.getViewCount());
        boardId = board.getId();
        writer = board.getUser().getName();
        title = board.getTitle();
        content = board.getContent();
    }
}
