package com.lol.community.comment.controller;

import com.lol.community.board.domain.Board;
import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentRequestDTO;
import com.lol.community.comment.service.CommentService;
import com.lol.community.user.domain.User;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentControllerTest {
  @Autowired
  private CommentService commentService;

  @Test
  public void writeCommentTest(){
    User user = User.builder().id(1).build();
    Board board = Board.builder().id(2).build();

//    CommentRequestDTO parent = CommentRequestDTO.builder()
//        .user_id(1L)
//        .board_id(2L)
//        .content("부모 입니다2.")
//        .build();
//
//    commentService.writeComment(parent);

    IntStream.rangeClosed(1,30).forEach(e->{
      CommentRequestDTO comment = CommentRequestDTO.builder()
          .user_id(1)
          .board_id(2)
          .content("테스트" + e)
          .build();
      commentService.writeComment(comment);
    });
  }

  @Test
  public void getCommentListTest(){
    Integer boardId = 2;

//    List<CommentResponseDTO> list = commentService.getCommentList(boardId);

//    System.out.println(list);
  }

  @Test
  public void updateCommentTest(){
    Integer commentId = 4;

    Comment comment = commentService.getComment(commentId);
    System.out.println(comment.getContent());

    CommentRequestDTO requestDTO = CommentRequestDTO.builder()
        .id(comment.getId())
        .user_id(comment.getUser().getId())
        .board_id(comment.getBoard().getId())
        .content("수정 테스트2")
        .build();

    commentService.updateComment(requestDTO);

    System.out.println(comment.getContent());

  }

  @Test
  public void deleteTest(){
    Integer commentId = 4;

    commentService.deleteComment(commentId);
  }
}
