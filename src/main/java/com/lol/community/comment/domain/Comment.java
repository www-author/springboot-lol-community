package com.lol.community.comment.domain;

import com.lol.community.board.domain.Board;
import com.lol.community.global.BaseEntity;
import com.lol.community.reaction.domain.LikeReactionComment;
import com.lol.community.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private Integer id;

  @Column(nullable = false)
  private String content;

  @ColumnDefault("0")
  @Column(name="like_count")
  private Long likeCount;

  @ColumnDefault("0")
  @Column(name="co_depth")
  private Integer co_depth;

  @Column(name="co_order")
  private Integer co_order;

  @ColumnDefault("FALSE")
  @Column(name= "is_deleted", nullable = false)
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Comment parent;

  @OneToMany(mappedBy = "parent", orphanRemoval = true)
  private List<Comment> children = new ArrayList<>();

  @OneToMany(mappedBy = "comment", orphanRemoval = true)
  private List<LikeReactionComment> likeReactionComments = new ArrayList<>();

  @Builder
  public Comment(Integer id, String content, Integer co_depth, Integer co_order, Board board, User user, Comment parent){
    this.id = id;
    this.content = content;
    this.co_depth = co_depth;
    this.co_order = co_order;
    this.board = board;
    this.user = user;
    this.isDeleted = false;
    this.parent = parent;
  }

  public void updateContent(String content){
    this.content = content;
  }

  public void updateParent(Comment parent){
    this.parent = parent;
  }

  public void updateLikeCount(Long value){
    this.likeCount += value;
  }

  public void updateDepth(Integer co_depth){
    this.co_depth = co_depth;
  }

  public void updateOrder(Integer co_order){
    this.co_order = co_order;
  }

  public void changeIsDelete(Boolean isDeleted){
    this.isDeleted = isDeleted;
    this.content = "삭제된 댓글입니다.";
  }
}
