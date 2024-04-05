package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.request.BoardSearchRequest;
import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.repository.BoardRepository;
import com.lol.community.comment.service.CommentService;
import com.lol.community.user.domain.User;
import com.lol.community.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final BoardReactionService boardReactionService;

    @Override
    public Board save(
            BoardRequest request
    ) {
        User user = userService.findUserById(request.getUserId());
        Board board = request.toEntityByUser(user);
        return boardRepository.save(board);
    }

    @Override
    public Board findById(Integer id) {
        return boardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. (id : " + id + ")"));
    }

    @Override
    @Transactional
    public Board modify(
            Integer id,
            BoardRequest request
    ) {
        Board board = findById(id);
        board.update(request);
        return board;
    }

    @Override
    public void deleteById(Integer id) {
        Board board = findById(id);
        boardRepository.delete(board);
    }

    @Override
    public Page<BoardResponse> findPageByBoardType(
            String boardType,
            Pageable pageable,
            BoardSearchRequest request
    ) {
        Page<Board> boardPage = Page.empty(pageable);

        if (request.isEmptyCategory() & request.isEmptyKeyword()) {
            boardPage = boardRepository.findAllByBoardTypeAndIsDeletedIsFalse(boardType, pageable);
        } else {
            boardPage = boardRepository.findAllByBoardTypeAndTitleContainingIgnoreCaseAndIsDeletedIsFalse(
                    boardType,
                    request.getKeyword(),
                    pageable
            );
        }

        List<BoardResponse> responses = new ArrayList<>();
        if (request.isEmptyCategory()) {
            responses = boardPage.stream()
                    .map(BoardResponse::new)
                    .toList();
        } else {
            responses = boardPage.stream()
                    .filter(board -> board.getCategory().getId().equals(request.getCategories()))
                    .map(BoardResponse::new)
                    .toList();
        }

        // TODO 권한 조회
        // TODO 1. 본인 권한 조회
        // TODO 2. 선택한 권한이 본인 권한을 초과하는지 체크
        if(BoardType.FREE.name().equals(boardType)) {

        }

        return new PageImpl<>(responses, pageable, boardPage.getTotalElements());
    }


    @Override
    public Map<String, List<BoardMainResponse>> getModelOfTopByMain(
            String boardType,
            Integer limit
    ) {
        Map<String, List<BoardMainResponse>> mainModelAttributes = new HashMap<>();
        mainModelAttributes.put("orderByViewCount", findTopByViewCount(boardType, limit));
        mainModelAttributes.put("orderByCommentCount", findTopByCommentCount(boardType, limit));
        mainModelAttributes.put("orderByLikeCount", findTopByLikeCount(boardType, limit));
        return mainModelAttributes;
    }


    // 조회 수
    @Override
    public List<BoardMainResponse> findTopByViewCount(
            String boardType,
            Integer limit
    ) {
        return boardRepository.findAllByBoardTypeAndIsDeletedIsFalseOrderByViewCountDesc(
                boardType,
                PageRequest.of(0, limit)
        )
                .stream()
                .map(BoardMainResponse::new)
                .toList();
    }

    // 추천 수
    @Override
    public List<BoardMainResponse> findTopByLikeCount(
            String boardType,
            Integer limit
    ) {
        return boardReactionService.findAllReactionGroupByWithBoard(boardType, limit);
    }

    // 댓글 수
    @Override
    public List<BoardMainResponse> findTopByCommentCount(
            String boardType,
            Integer limit
    ) {
        return commentService.findAllCommentByGroupByWithBoard(boardType, limit);
    }
}
