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
    private Long userId;
    private Long categoryId;
    private BoardType boardType;
    private String title;
    private String content;

    public Board toEntity() {
        User user = User.builder().id(userId).build();

        return Board.builder()
                .user(user)
                .category(new Category(this.categoryId))
                .boardType(this.boardType.name())
                .title(this.title)
                .content(this.content)
                .build();
    }
}
