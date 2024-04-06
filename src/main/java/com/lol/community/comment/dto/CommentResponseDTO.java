package com.lol.community.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lol.community.comment.domain.Comment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDTO {
  private Long id;
  private String userName;
  private String content;
  private Integer co_depth;
  private Integer co_order;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime create_at;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updated_at;
  private Long likeCount;
  @Setter
  private List<CommentResponseDTO> children;

  @Builder
  public CommentResponseDTO(Long id, String userName, String content, Integer co_depth, Integer co_order, LocalDateTime create_at,
      LocalDateTime updated_at,Long likeCount) {
    this.id = id;
    this.userName = userName;
    this.content = content;
    this.co_depth = co_depth;
    this.co_order = co_order;
    this.create_at = create_at;
    this.updated_at = updated_at;
    this.likeCount = likeCount;
  }

  public static CommentResponseDTO entityToDTO(Comment comment){

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

//    if(comment.getChildren().size() > 0){
//      List<CommentResponseDTO> childrenDTO = comment.getChildren().stream().map(e->entityToDTO(e)).toList();
//
//      commentResponseDTO.setChildren(childrenDTO);
//
//    }

    return commentResponseDTO;
  }
}
