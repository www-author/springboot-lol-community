package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board save(BoardRequest request) {
        return boardRepository.save(request.toEntity());
    }
}
