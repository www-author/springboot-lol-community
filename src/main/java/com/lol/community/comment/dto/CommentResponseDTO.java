package com.lol.community.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lol.community.comment.domain.Comment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentResponseDTO {
  private Integer id;
  private String userName;
  private Integer user_id;
  private String content;
  private Integer co_depth;
  private Integer co_order;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime created_at;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updated_at;
  private Integer like_user_id;
  private Long likeCount;
  private Boolean isLike = false;
  private Boolean isAuth = false;
  @Setter
  private List<CommentResponseDTO> children;

  @Builder
  public CommentResponseDTO(Integer id, String userName, Integer user_id, String content,
      Integer co_depth, Integer co_order, LocalDateTime create_at, LocalDateTime updated_at,
      Integer like_user_id, Long likeCount) {
    this.id = id;
    this.userName = userName;
    this.user_id = user_id;
    this.content = content;
    this.co_depth = co_depth;
    this.co_order = co_order;
    this.created_at = create_at;
    this.updated_at = updated_at;
    this.like_user_id = like_user_id;
    this.likeCount = likeCount;
  }

  @Builder
  public CommentResponseDTO(Integer id, String userName, String content, Integer co_depth,
      Integer co_order, LocalDateTime create_at, LocalDateTime updated_at, Long likeCount) {
    this.id = id;
    this.userName = userName;
    this.content = content;
    this.co_depth = co_depth;
    this.co_order = co_order;
    this.created_at = create_at;
    this.updated_at = updated_at;
    this.likeCount = likeCount;
  }

  public static CommentResponseDTO entityToDTO(Comment comment, Integer user_id){

    CommentResponseDTO commentResponseDTO = CommentResponseDTO.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .userName(comment.getUser().getName())
        .co_depth(comment.getCo_depth())
        .co_order(comment.getCo_order())
        .likeCount(comment.getLikeCount())
        .create_at(comment.getCreatedAt())
        .updated_at(comment.getUpdatedAt())
        .build();

    if(comment.getUser().getId() == user_id){
      commentResponseDTO.updateIsAuth(true);
    }

    return commentResponseDTO;
  }

  public void checkIsLike(Integer user_id){
    if(like_user_id == user_id){
      isLike = true;
    }else{
      isLike = false;
    }
  }

  public void checkIsAuth(Integer user_id){
    if(this.user_id == user_id){
      isAuth = true;
    }else{
      isAuth = false;
    }
  }

  public void updateIsLike(boolean isLike){
    this.isLike = isLike;
  }

  public void updateIsAuth(boolean isAuth){
    this.isAuth = isAuth;
  }
}
