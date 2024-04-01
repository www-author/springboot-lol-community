package com.lol.community.board.repository;

import com.lol.community.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findAllByBoardType(String boardType, Pageable pageable);
}
