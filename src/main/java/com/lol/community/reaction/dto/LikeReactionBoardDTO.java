package com.lol.community.reaction.dto;

import com.lol.community.board.domain.Board;
import com.lol.community.reaction.domain.LikeReactionBoard;
import com.lol.community.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReactionBoardDTO {

  private Long user_id;
  private Long board_id;

  @Builder
  public LikeReactionBoardDTO(Long user_id, Long board_id) {
    this.user_id = user_id;
    this.board_id = board_id;
  }

  public LikeReactionBoard dtoToEntity(User user, Board board){
    return LikeReactionBoard.builder()
        .user(user)
        .board(board)
        .build();
  }

  public static LikeReactionBoardDTO entityToDTO(LikeReactionBoard likeReactionBoard){

    return LikeReactionBoardDTO.builder()
        .user_id(likeReactionBoard.getUser().getId())
        .board_id(likeReactionBoard.getBoard().getId())
        .build();
  }
}
