package com.lol.community.board.dto.request;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.category.domain.Category;
import com.lol.community.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {
    private Integer categoryId;
    private BoardType boardType;
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 255, message = "제목은 최대 255글자까지 입력할 수 있습니다.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public Board toEntityByUser(User user) {
        return Board.builder()
                .user(user)
                .category(new Category(this.categoryId))
                .boardType(this.boardType.name())
                .title(this.title)
                .content(this.content)
                .build();
    }
}
