package com.lol.community.board.dto.request;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.category.domain.Category;
import com.lol.community.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {
    private Integer userId;
    private Integer categoryId;
    private BoardType boardType;
    private String title;
    private String content;

    public Board toEntityByUser(User user) {
        return Board.builder()
                .user(User.builder()
                        .id(this.userId)
                        .build())
                .category(new Category(this.categoryId))
                .boardType(this.boardType.name())
                .title(this.title)
                .content(this.content)
                .build();
    }
}
