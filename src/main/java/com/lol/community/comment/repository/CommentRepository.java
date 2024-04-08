package com.lol.community.comment.repository;

import com.lol.community.board.dto.response.BoardMainView;
import com.lol.community.comment.Comment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select count(comment.board.id) as totalCount, " +
            "comment.board.id as boardId, " +
            "comment.user.name as writer, " +
            "comment.board.title as title, " +
            "comment.board.content as content " +
            "from Comment as comment " +
            "join comment.board " +
            "where comment.board.isDeleted is false " +
            "and comment.isDeleted is false " +
            "and comment.board.boardType = :boardType " +
            "group by comment.board.id ")
    List<BoardMainView> findGroupByCommentOfBoard(@Param("boardType") String boardType, PageRequest pageRequest);
}
