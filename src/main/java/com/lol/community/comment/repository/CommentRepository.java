package com.lol.community.comment.repository;

import com.lol.community.board.dto.response.BoardMainView;
import com.lol.community.comment.domain.Comment;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer>, CustomCommentRepository {

  Comment findCommentById(Integer id);

  @Modifying
  @Query("update Comment c set c.co_order = c.co_order + 1 where c.board.id = :board_id and c.co_order > :co_order")
  void pushBackOrder(Integer board_id, Integer co_order);

  @Query("select coalesce(max(c.co_order),0) + 1 FROM Comment c where c.board.id = :board_id")
  Integer getMaxOrder(Integer board_id);

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
      "group by comment.board.id, comment.user.name, comment.board.title, comment.board.content")
  List<BoardMainView> findGroupByCommentOfBoard(@Param("boardType") String boardType, PageRequest pageRequest);
}
