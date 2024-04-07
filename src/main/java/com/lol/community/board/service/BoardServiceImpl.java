package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.repository.BoardRepository;
import com.lol.community.user.domain.User;
import com.lol.community.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public Board save(
            BoardRequest request,
            String email
    ) {
        User user = userRepository.findByEmail(email);
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
            BoardType boardType,
            Pageable pageable
    ) {
        Page<Board> boardPage = boardRepository.findAllByBoardType(boardType.name(), pageable);
        List<BoardResponse> responses = boardPage.stream()
                .map(BoardResponse::new)
                .toList();
        return new PageImpl<>(responses, pageable, boardPage.getTotalElements());
    }

}
