package com.lol.community.reaction.controller;

import com.lol.community.reaction.dto.LikeReactionBoardDTO;
import com.lol.community.reaction.dto.LikeReactionCommentDTO;
import com.lol.community.reaction.service.LikeReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/comment/like")
  public ResponseEntity<Long> updateLikeComment(@RequestBody LikeReactionCommentDTO likeReactionCommentDTO){
    log.info(likeReactionCommentDTO);

    Long likeCount = likeReactionService.changeLikeComment(likeReactionCommentDTO);

    return ResponseEntity.ok().body(likeCount);
  }

  @PostMapping("/board/like")
  public ResponseEntity<Long> updateLikeBoard(@RequestBody LikeReactionBoardDTO likeReactionBoardDTO){
    log.info(likeReactionBoardDTO);

    Long likeCount = likeReactionService.changeLikeBoard(likeReactionBoardDTO);

    log.info(likeCount);

    return ResponseEntity.ok().body(likeCount);
  }
}
