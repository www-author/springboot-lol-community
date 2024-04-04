package com.lol.community.comment;


import com.lol.community.board.domain.Board;
import com.lol.community.global.BaseEntity;
import com.lol.community.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(
             name = "board_id",
             referencedColumnName = "id",
             foreignKey = @ForeignKey(name = "fk_comment_board"),
             nullable = false
     )
     private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(
             name = "user_id",
             referencedColumnName = "id",
             foreignKey = @ForeignKey(name = "fk_comment_user"),
             nullable = false
     )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Column(name= "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount;
}
