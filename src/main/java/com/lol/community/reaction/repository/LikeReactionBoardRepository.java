package com.lol.community.reaction.repository;

import com.lol.community.board.domain.Board;
import com.lol.community.reaction.domain.LikeReactionBoard;
import com.lol.community.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeReactionBoardRepository extends JpaRepository<LikeReactionBoard, Long> {

  LikeReactionBoard findByUserAndBoard(User user, Board board);
}
