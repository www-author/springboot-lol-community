package com.lol.community.board.repository;

import com.lol.community.board.domain.BoardReaction;
import com.lol.community.board.dto.response.BoardMainView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardReactionRepository extends JpaRepository<BoardReaction, Integer> {
    @Query("select count(reactoin.board.id) as totalCount, " +
            "reactoin.board.id as boardId, " +
            "reactoin.board.user.name as writer, " +
            "reactoin.board.title as title, " +
            "reactoin.board.content as content " +
            "from BoardReaction as reactoin " +
            "join reactoin.board " +
            "where reactoin.board.isDeleted is false " +
            "and reactoin.board.boardType =:boardType " +
            "group by reactoin.board.id")
    List<BoardMainView> findBoardsWithCount(@Param("boardType") String boardType, PageRequest pageRequest);

    Boolean existsBoardReactionByBoardIdAndUserId(Integer boardId, Integer userId);
}

