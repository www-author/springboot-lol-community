package com.lol.community.freeBoard.service;

import com.lol.community.board.domain.Board;
import com.lol.community.category.domain.Category;
import com.lol.community.freeBoard.dto.FreeBoardDTO;
import com.lol.community.user.domain.User;
import java.util.List;

public interface FreeBoardService {

  FreeBoardDTO getArticle(Long articleId);

  List<Board> getArticleList();

  Long write(FreeBoardDTO freeBoardDTO);

  Long modify(FreeBoardDTO freeBoardDTO);

  default Board dtoToEntity(FreeBoardDTO freeBoardDTO){

    User user = User.builder().id(freeBoardDTO.getWriterId()).build();

    Category category = switch (freeBoardDTO.getCategory()) {
      case "n" -> Category.builder().id(1L).build();
      case "i" -> Category.builder().id(2L).build();
      case "q" -> Category.builder().id(3L).build();
      default -> null;
    };

    Board board = Board.builder()
        .id(freeBoardDTO.getId())
        .title(freeBoardDTO.getTitle())
        .boardType("FREE")
        .category(category)
        .content(freeBoardDTO.getContent())
        .user(user)
        .isDeleted(Boolean.FALSE)
        .viewCount(0)
        .likeCount(0)
        .dislikeCount(0)
        .build();

    return board;
  }

  default FreeBoardDTO entityToDTO(Board board, Long replyCount){

    String category = switch (board.getCategory().getCategoryName()) {
      case "잡답" -> "n";
      case "정보" -> "i";
      case "질문" -> "q";
      default -> null;
    };

    FreeBoardDTO freeBoardDTO = FreeBoardDTO.builder()
        .id(board.getId())
        .title(board.getTitle())
        .content(board.getContent())
        .writerId(board.getUser().getId())
        .writerName(board.getUser().getName())
        .category(category)
        .created_at(board.getCreatedAt())
        .updated_at(board.getUpdatedAt())
        .replyCount(replyCount)
        .build();

    return freeBoardDTO;
  }

}
