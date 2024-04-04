package com.lol.community.board.service;

import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.board.repository.BoardReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardReactionService {
    private final BoardReactionRepository boardReactionRepository;

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
}
