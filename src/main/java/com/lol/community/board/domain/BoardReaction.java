package com.lol.community.board.domain;


import com.lol.community.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "board_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_reaction_board"),
            nullable = false
    )
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_reaction_user"),
            nullable = false
    )
    private User user;

    @Builder
    public BoardReaction(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
