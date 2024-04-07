package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.response.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<BoardResponse> findPageByBoardType(BoardType boardType, Pageable pageable);

    Board save(BoardRequest request, String email);

    Board findById(Integer id);

    Board modify(Integer id, BoardRequest request);

    void deleteById(Integer id);
}
