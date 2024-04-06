package com.lol.community.comment.controller;

import com.lol.community.comment.dto.CommentRequestDTO;
import com.lol.community.comment.dto.CommentResponseDTO;
import com.lol.community.comment.service.CommentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @GetMapping("/list")
  public String initCommentList(Long board_id, Integer page, String request , Model model) {

    if(page == 1){
      List<CommentResponseDTO> bestCommentList = commentService.getBestComment(board_id);
      model.addAttribute("bestCommentList", bestCommentList);
    }

    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("boardId", board_id);
    resultMap.put("page", page);

    model.addAttribute("commentResult", commentService.getCommentList(board_id, page));
    model.addAttribute("resultMap",resultMap);

    if(request.equals("refresh")){
      return "/comment/list :: #all-comment";
    }

    return "/comment/list";
  }

  @ResponseBody
  @PostMapping("/write")
  public ResponseEntity<CommentResponseDTO> writeComment(@RequestBody CommentRequestDTO commentRequestDTO){
    commentService.writeComment(commentRequestDTO);

    return ResponseEntity.ok().build();
  }

  @ResponseBody
  @PutMapping("/update")
  public ResponseEntity<String> updateComment(@RequestBody CommentRequestDTO commentRequestDTO){
    commentService.updateComment(commentRequestDTO);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteComment(@RequestParam("comment_id") Long comment_id){
    log.info("삭제 시작 " + comment_id);
    boolean result = commentService.deleteComment(comment_id);

    if(result){
      return ResponseEntity.ok("삭제 성공");
    }else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
    }
  }
}
