package com.lol.community.reaction.dto;

import com.lol.community.comment.domain.Comment;
import com.lol.community.reaction.domain.LikeReactionComment;
import com.lol.community.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReactionCommentDTO {
  private Integer user_id;
  private Integer comment_id;

  @Builder
  public LikeReactionCommentDTO(Integer user_id, Integer comment_id) {
    this.user_id = user_id;
    this.comment_id = comment_id;
  }

  public LikeReactionComment dtoToEntity(User user, Comment comment){
    return LikeReactionComment.builder()
        .user(user)
        .comment(comment)
        .build();
  }

  public static LikeReactionCommentDTO entityToDTO(LikeReactionComment likeReactionComment){

    return LikeReactionCommentDTO.builder()
        .user_id(likeReactionComment.getUser().getId())
        .comment_id(likeReactionComment.getComment().getId())
        .build();

  }
}
