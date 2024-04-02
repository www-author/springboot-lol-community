package com.lol.community.board.controller;

import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.service.BoardService;
import com.lol.community.category.dto.CategoryResponse;
import com.lol.community.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CategoryService categoryService;
    @GetMapping("/report")
    public String getArticlesOfReport(
            @PageableDefault(
                    size = 15,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            Model model
    ) {
        String boardType = BoardType.REPORT.name();
        List<CategoryResponse> categoryResponses = categoryService.findCategoriesByBoardType(boardType);
        model.addAttribute("categories", categoryResponses);
        model.addAttribute("articles", boardService.findPageByBoardType(BoardType.REPORT, pageable));
        model.addAttribute("boardType", boardType);
        return "board";
    }

    @GetMapping("/free")
    public String getArticlesOfFree(
            @PageableDefault(
                    size = 15,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            Model model
    ) {
        String boardType = BoardType.FREE.name();
        List<CategoryResponse> categoryResponses = categoryService.findCategoriesByBoardType(boardType);
        model.addAttribute("categories", categoryResponses);
        model.addAttribute("articles", boardService.findPageByBoardType(BoardType.FREE, pageable));
        model.addAttribute("boardType", boardType);
        return "board";
    }


    @GetMapping("/new/{boardType}")
    public String showArticle(
            @PathVariable("boardType") String type,
            Model model
    ) {
        String boardType = BoardType.valueOf(type.toUpperCase()).name();
        List<CategoryResponse> categoryResponses = categoryService.findCategoriesByBoardType(boardType);
        BoardResponse boardResponse = BoardResponse.builder()
                .boardType(boardType)
                .build();
        model.addAttribute("categories", categoryResponses);
        model.addAttribute("article", boardResponse);
        return "newArticle";
    }
}
