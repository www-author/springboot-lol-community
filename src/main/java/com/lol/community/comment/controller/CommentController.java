package com.lol.community.comment.controller;

import com.lol.community.board.domain.Board;
import com.lol.community.board.service.BoardService;
import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentRequestDTO;
import com.lol.community.comment.dto.CommentResponseDTO;
import com.lol.community.comment.service.CommentService;
import com.lol.community.reaction.service.LikeReactionService;
import com.lol.community.user.domain.User;
import com.lol.community.user.login.Login;
import com.lol.community.user.login.SessionValue;
import com.lol.community.user.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

  private final CommentService commentService;
  private final UserService userService;
  private final BoardService boardService;
  private final LikeReactionService likeReactionService;

  @GetMapping("/list")
  public String initCommentList(Integer board_id, Integer page, @Login SessionValue sessionValue, Model model) {

    User user = getUserInfo(sessionValue);
    Board board = getBoardInfo(board_id);

    if(page == 1){
      List<CommentResponseDTO> bestCommentList = commentService.getBestComment(board.getId());
      model.addAttribute("bestCommentList", bestCommentList);
    }

    model.addAttribute("user_id", user.getId());
    model.addAttribute("user_name", user.getName());
    model.addAttribute("commentResult", commentService.getCommentList(board.getId(), page, user.getId()));
    model.addAttribute("article",board);

    return "/board/article :: #all-comment";
  }

  @ResponseBody
  @PostMapping("/write")
  public ResponseEntity<CommentResponseDTO> writeComment(@RequestBody CommentRequestDTO commentRequestDTO, @Login SessionValue sessionValue){
    User user = getUserInfo(sessionValue);
    getBoardInfo(commentRequestDTO.getBoard_id());

    commentRequestDTO.setUser_id(user.getId());

    commentService.writeComment(commentRequestDTO);

    return ResponseEntity.ok().build();
  }

  @ResponseBody
  @PutMapping("/update")
  public ResponseEntity<String> updateComment(@RequestBody CommentRequestDTO commentRequestDTO, @Login SessionValue sessionValue){
    User user = getUserInfo(sessionValue);

    if(commentRequestDTO.getUser_id() != user.getId()){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("작성자가 아닙니다.");
    }

    commentService.updateComment(commentRequestDTO);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteComment(@RequestParam("comment_id") Integer comment_id, @Login SessionValue sessionValue){

    User user = getUserInfo(sessionValue);
    Comment comment = commentService.getComment(comment_id);

    if(comment.getUser().getId() != user.getId()){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("작성자가 아닙니다.");
    }

    boolean result = commentService.deleteComment(comment_id);

    if(result){
      return ResponseEntity.ok("삭제 성공");
    }else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
    }
  }

  protected User getUserInfo(SessionValue sessionValue){
    if(sessionValue.getUserId() == null){
      throw new NoSuchElementException("로그인 해주세요.");
    }

    User user = userService.findUserById(sessionValue.getUserId());
    if(user == null){
      throw new NoSuchElementException("없는 유저입니다.");
    }

    return user;
  }

  protected Board getBoardInfo(Integer board_id){
    Board board = boardService.findById(board_id);

    if(board == null){
      throw new NoSuchElementException("존재하지 않는 글입니다.");
    }

    return board;
  }
}
