package com.lol.community.board.domain;

import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.category.domain.Category;
import com.lol.community.global.BaseEntity;
import com.lol.community.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_board_category"),
            nullable = false
    )
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_board_user"),
            nullable = false
    )
    private User user;

    @Column(name = "board_type", nullable = false)
    private String boardType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "is_deleted", length = 1, nullable = false)
    private Boolean isDeleted;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount;

    @Builder
    public Board(
            User user,
            Category category,
            String boardType,
            String title,
            String content
    ) {
        this.user = user;
        this.category = category;
        this.boardType = boardType;
        this.title = title;
        this.content = content;
        this.isDeleted = Boolean.FALSE;
        this.viewCount = 0;
        this.likeCount = 0;
        this.dislikeCount = 0;

    }

    public BoardResponse toResponse() {
        return BoardResponse.builder()
                .id(this.id)
                .categoryId(this.category.getId())
                .userId(this.user.getId())
                .boardType(this.boardType)
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .likeCount(this.likeCount)
                .dislikeCount(this.dislikeCount)
                .isDeleted(this.isDeleted)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    public void modify(String title, String content, String category){
        this.title = title;
        this.content = content;
//        this.category = category;
    }
}
