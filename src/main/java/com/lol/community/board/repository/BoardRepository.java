package com.lol.community.board.repository;

import com.lol.community.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findAllByBoardTypeAndIsDeletedIsFalse(String boardType, Pageable pageable);

    Page<Board> findAllByBoardTypeAndTitleContainingIgnoreCaseAndIsDeletedIsFalse(
            String boardType,
            String keyword,
            Pageable pageable
    );

    List<Board> findAllByBoardTypeAndIsDeletedIsFalseOrderByViewCountDesc(String boardType, PageRequest pageRequest);
}
