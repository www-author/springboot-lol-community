package com.lol.community.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.repository.BoardRepository;
import com.lol.community.category.domain.Category;
import com.lol.community.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BoardApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private BoardRepository boardRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }
    @Test
    void 게시글_DB_저장_단위_테스트() {
        //given
        Mockito.when(boardRepository.save(any()))
                .thenAnswer(invocation -> {
                   return invocation.getArgument(0);
                });

        Board board = new Board(
                new User(1),
                new Category(3),
                BoardType.REPORT.name(),
                "title",
                "내용1"
        );

        //when
        Board saveBoard = boardRepository.save(board);

        //then
        assertEquals(saveBoard, board);
    }

    @Test
    void 게시글_생성에_대한_통합_테스트() throws Exception {
        // given
        BoardRequest request = new BoardRequest(
         1,
         3,
          BoardType.REPORT,
         "title",
         "content"
        );

        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/board/write")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());
    }
}