package com.lol.community.freeBoard.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.lol.community.board.domain.Board;
import com.lol.community.freeBoard.dto.FreeBoardDTO;
import com.lol.community.freeBoard.service.FreeBoardService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class FreeBoardControllerTest {

  @Autowired
  private FreeBoardService freeBoardService;

  @DisplayName("글 등록")
  @Test
  public void write() throws Exception{
    //given
    String url = "/board/free/write";

    FreeBoardDTO freeBoardDTO = FreeBoardDTO.builder()
        .title("title")
        .content("content")
        .writerId(1L)
        .categoryId(1L)
        .build();

    freeBoardService.write(freeBoardDTO);

    List<Board> boardList = freeBoardService.getFreeBoardList();

    assertThat(boardList.size()).isEqualTo(1);
    assertThat(boardList.get(0).getTitle()).isEqualTo("title");
    assertThat(boardList.get(0).getContent()).isEqualTo("content");

  }

}