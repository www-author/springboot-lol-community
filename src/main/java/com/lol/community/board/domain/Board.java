package com.lol.community.board.domain;

import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.category.domain.Category;
import com.lol.community.global.BaseEntity;
import com.lol.community.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

@DynamicInsert
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
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

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ColumnDefault("0")
    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Builder
    public Board(
            Long id,
            User user,
            Category category,
            String boardType,
            String title,
            String content
    ) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.boardType = boardType;
        this.title = title;
        this.content = content;
        this.isDeleted = Boolean.FALSE;
        this.viewCount = 0;
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
                .isDeleted(this.isDeleted)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    // TODO 파일 변경 등 엔티티도 고려
    public void update(BoardRequest request) {
        this.category = request.toEntity().getCategory();
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void updateLikeCount(Integer likeCount) {
        this.likeCount += likeCount;
    }
}
