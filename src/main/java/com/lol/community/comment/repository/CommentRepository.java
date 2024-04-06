package com.lol.community.comment.repository;

import com.lol.community.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {

  @Modifying
  @Query("update Comment c set c.co_order = c.co_order + 1 where c.board.id = :board_id and c.co_order > :co_order")
  void pushBackOrder(Long board_id, Integer co_order);

  @Query("select coalesce(max(c.co_order),0) + 1 FROM Comment c where c.board.id = :board_id")
  Integer getMaxOrder(Long board_id);
}
