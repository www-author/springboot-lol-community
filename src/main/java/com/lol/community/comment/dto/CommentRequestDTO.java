package com.lol.community.comment.dto;

import com.lol.community.board.domain.Board;
import com.lol.community.comment.domain.Comment;
import com.lol.community.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDTO {
  private Long id;
  private Long user_id;
  private Long board_id;
  private Long parent_id;
  private String content;
  private Integer depth;
  private Integer order;

  @Builder
  public CommentRequestDTO(Long id, Long user_id, Long board_id, String content){
    this.id = id;
    this.user_id = user_id;
    this.board_id = board_id;
    this.content = content;
    this.depth = 0;
  }

  public void updateParentId(Long parent_id){
    this.parent_id = parent_id;
  }

  public Comment dtoToEntity(){
    User user = User.builder().id(this.getUser_id()).build();
    Board board = Board.builder().id(this.getBoard_id()).build();

    return Comment.builder()
        .user(user)
        .board(board)
        .content(this.getContent())
        .build();
  }
}
