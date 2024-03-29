package com.lol.community.freeBoard.controller;

import com.lol.community.freeBoard.dto.FreeBoardDTO;
import com.lol.community.freeBoard.service.FreeBoardService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/free")
@Log4j2
@RequiredArgsConstructor
public class FreeBoardController {

  private final FreeBoardService freeBoardService;

  @GetMapping("/list")
  public String freeBoardList(Model model){
    List<FreeBoardDTO> freeBoardDTO = new ArrayList<>();

    model.addAttribute("dtoList", freeBoardDTO);

    return "/freeBoard/list";
  }

  @GetMapping("/article/{article_id}")
  public String freeBoardArticleRead(@PathVariable Long article_id, Model model){
    log.info(article_id);
    FreeBoardDTO freeBoardDTO = freeBoardService.getArticle(article_id);
    log.info(freeBoardDTO);
    model.addAttribute("dto", freeBoardDTO);
    return "/freeBoard/read";
  }

  @GetMapping("/article")
  public String freeBoardWrite(){

    return "/freeBoard/write";
  }

  @PostMapping("/article")
  public String freeBoardWritePost(FreeBoardDTO freeBoardDTO, RedirectAttributes redirectAttributes, HttpSession httpSession){
    log.info(freeBoardDTO);
    Long userId = (Long)httpSession.getAttribute("id");
    freeBoardDTO.setWriterId(userId);
    Long id = freeBoardService.write(freeBoardDTO);
    log.info(id);
    return "redirect:/freeBoard/list";
  }

  @PutMapping("/article")
  public String freeBoardModify(@RequestBody FreeBoardDTO freeBoardDTO, RedirectAttributes redirectAttributes){

    Long articleId = freeBoardService.modify(freeBoardDTO);
    log.info(articleId);
    return "redirect:/freeBoard/read";
  }

}
