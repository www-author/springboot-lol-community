package com.lol.community.board.repository;

import com.lol.community.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    Page<Board> findAllByBoardTypeAndIsDeletedIsFalse(String boardType, Pageable pageable);

    Page<Board> findAllByBoardTypeAndTitleContainingIgnoreCaseAndIsDeletedIsFalse(
            String boardType,
            String keyword,
            Pageable pageable
    );

    List<Board> findAllByBoardTypeAndIsDeletedIsFalseOrderByViewCountDesc(String boardType, PageRequest pageRequest);

    @Query(
            "select board " +
            "from Board board " +
            "join fetch board.user " +
            "where board.isDeleted = false " +
            "and board.boardType = :boardType " +
            "and board.user.grade in(:grades) "
    )
    Page<Board> findBoardsByBoardTypeWithGrades(
            @Param("boardType") String boardType,
            @Param("grades") List<String> grades,
            Pageable pageable
    );

    Page<Board> findAllByBoardTypeAndCategoryIdAndIsDeletedIsFalse(
            String boardType,
            Integer categoryId,
            Pageable pageable
    );

    Page<Board> findAllByBoardTypeAndIsDeletedIsFalseAndCategoryIdAndTitleContainingIgnoreCase(
            String boardType,
            Integer categoryId,
            String keyword,
            Pageable pageable
    );


}
