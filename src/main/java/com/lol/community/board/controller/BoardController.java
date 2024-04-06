package com.lol.community.board.controller;

import com.lol.community.board.domain.Board;
import com.lol.community.board.service.BoardService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/{article_id}")
  public String getList(@PathVariable Long article_id, Model model) {
    Board board = boardService.findById(article_id);
    if(board == null) {
      throw new NoSuchElementException("존재하지 않는 글입니다.");
    }

    model.addAttribute("article", board);

    System.out.println(board);

    return "board/article";
  }
}
