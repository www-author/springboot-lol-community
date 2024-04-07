package com.lol.community.board.dto;

import com.lol.community.board.domain.Board;
import com.lol.community.board.dto.request.BoardSearchRequest;
import com.lol.community.board.dto.response.BoardResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearch {
    private String boardType;
    private Pageable pageable;
    private BoardSearchRequest searchRequest;
    private Page<Board> boardPage;
    private List<BoardResponse> responses;

    public static BoardSearch of(
        String boardType,
        Pageable pageable,
        BoardSearchRequest searchRequest,
        Page<Board> boardPage,
        List<BoardResponse> responses
    ) {
        return new BoardSearch(
                boardType,
                pageable,
                searchRequest,
                boardPage,
                responses
        );
    }
}
