package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board save(BoardRequest request) {
        return boardRepository.save(request.toEntity());
    }

    public Board findById(Long id) {
        return boardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. (id : " + id + ")"));
    }

    @Transactional
    public Board modify(
            Long id,
            BoardRequest request
    ) {
        Board board = findById(id);
        board.update(request);
        return board;
    }
}
