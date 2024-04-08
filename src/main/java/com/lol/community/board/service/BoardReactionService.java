package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardReaction;
import com.lol.community.board.dto.request.BoardReactionDTO;
import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.board.repository.BoardReactionRepository;
import com.lol.community.board.repository.BoardRepository;
import com.lol.community.user.domain.User;
import com.lol.community.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardReactionService {
    private final BoardReactionRepository boardReactionRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<BoardMainResponse> findAllReactionGroupByWithBoard(
            String boardType,
            Integer limit
    ) {
        return boardReactionRepository.findBoardsWithCount(
                boardType,
                PageRequest.of(0, limit)
        )
                .stream()
                .map(view -> BoardMainResponse.builder()
                .totalCount(view.getTotalCount())
                .title(view.getTitle())
                .content(view.getContent())
                .writer(view.getWriter())
                .boardId(view.getBoardId())
                .build())
                .toList();
    }

    @Transactional
    public Integer addLikeArticle(BoardReactionDTO boardReactionDTO) {
        Board board = boardRepository.findById(boardReactionDTO.getBoardId()).orElseThrow();
        User user = userRepository.findById(boardReactionDTO.getUserId()).orElseThrow();

        BoardReaction boardReaction = BoardReaction.builder()
            .board(board)
            .user(user)
            .build();

        board.updateLikeCount(1);
        boardReactionRepository.save(boardReaction);

        return board.getLikeCount();
    }

    @Transactional
    public Integer subLikeArticle(BoardReactionDTO boardReactionDTO) {
        Board board = boardRepository.findById(boardReactionDTO.getBoardId()).orElseThrow();
        User user = userRepository.findById(boardReactionDTO.getUserId()).orElseThrow();

        BoardReaction boardReaction = BoardReaction.builder()
            .board(board)
            .user(user)
            .build();

        board.updateLikeCount(-1);
        boardReactionRepository.delete(boardReaction);

        return board.getLikeCount();
    }

    public Boolean checkIsLikeArticle(Integer boardId, Integer userId){

        return boardReactionRepository.existsBoardReactionByBoardIdAndUserId(boardId, userId);
    }
}
