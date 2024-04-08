package com.lol.community.reaction.controller;

import com.lol.community.board.dto.request.BoardReactionDTO;
import com.lol.community.board.service.BoardReactionService;
import com.lol.community.reaction.dto.LikeReactionCommentDTO;
import com.lol.community.reaction.service.LikeReactionService;
import com.lol.community.user.login.Login;
import com.lol.community.user.login.SessionValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reaction")
@Log4j2
@RequiredArgsConstructor
public class LikeReactionController {
  private final LikeReactionService likeReactionService;
  private final BoardReactionService boardReactionService;

  @PostMapping("/comment/like")
  public ResponseEntity<Long> addLikeComment(@RequestBody LikeReactionCommentDTO likeReactionCommentDTO, @Login SessionValue sessionValue){
    log.info(likeReactionCommentDTO.getComment_id() + "," + likeReactionCommentDTO.getUser_id());
    likeReactionCommentDTO.setUser_id(sessionValue.getUserId());

    Long likeCount = likeReactionService.addLikeComment(likeReactionCommentDTO);

    return ResponseEntity.ok().body(likeCount);
  }

  @DeleteMapping("/comment/like")
  public ResponseEntity<Long> subLikeComment(@RequestBody LikeReactionCommentDTO likeReactionCommentDTO, @Login SessionValue sessionValue){
    log.info(likeReactionCommentDTO);
    likeReactionCommentDTO.setUser_id(sessionValue.getUserId());

    Long likeCount = likeReactionService.subLikeComment(likeReactionCommentDTO);

    return ResponseEntity.ok().body(likeCount);
  }

  @PostMapping("/board/like")
  public ResponseEntity<Integer> addLikeBoard(@RequestBody BoardReactionDTO boardReactionDTO, @Login SessionValue sessionValue){
    log.info(boardReactionDTO);
    boardReactionDTO.setUserId(sessionValue.getUserId());

    Integer likeCount = boardReactionService.addLikeArticle(boardReactionDTO);

    System.out.println(likeCount);

    return ResponseEntity.ok().body(likeCount);
  }

  @DeleteMapping("/board/like")
  public ResponseEntity<Integer> subLikeBoard(@RequestBody BoardReactionDTO boardReactionDTO, @Login SessionValue sessionValue){
    log.info(boardReactionDTO);
    boardReactionDTO.setUserId(sessionValue.getUserId());

    Integer likeCount = boardReactionService.subLikeArticle(boardReactionDTO);

    System.out.println(likeCount);

    return ResponseEntity.ok().body(likeCount);
  }
}
