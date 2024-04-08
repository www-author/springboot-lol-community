package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.request.BoardSearchRequest;
import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.board.dto.response.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BoardService {
    Page<BoardResponse> findPageByBoardType(String boardType, Pageable pageable, BoardSearchRequest request, Integer userId);

    Board save(BoardRequest request);

    Board findById(Integer id);

    Board modify(Integer id, BoardRequest request);

    void deleteById(Integer id);

    List<BoardMainResponse> findTopByViewCount(String boardType, Integer limit);

    List<BoardMainResponse> findTopByLikeCount(String boardType, Integer limit);

    List<BoardMainResponse> findTopByCommentCount(String boardType, Integer limit);
    Map<String, List<BoardMainResponse>> getModelOfTopByMain(String boardType, Integer limit);
    Page<Board> findAll(String boardType, Pageable pageable);
    Page<Board> findAllByTitle(String boardType, String keyword, Pageable pageable);

    Page<Board> findAllByCategoryId(String boardType, Integer category, Pageable pageable);
    Page<Board> findAllByCategoryIdAndTitle(String boardType, Integer category, String keyword, Pageable pageable);
}
