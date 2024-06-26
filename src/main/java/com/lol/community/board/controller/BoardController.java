package com.lol.community.board.controller;

import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.request.BoardSearchRequest;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.service.BoardService;
import com.lol.community.category.dto.response.CategoryResponse;
import com.lol.community.category.service.CategoryService;
import com.lol.community.user.domain.User;
import com.lol.community.user.login.Login;
import com.lol.community.user.login.SessionValue;
import com.lol.community.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.lol.community.user.domain.Grade.getGrades;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/main")
    public String showMain(
            @Login SessionValue sessionValue,
            Model model
    ) {
        addModelAttributeOfUserInfo(sessionValue, model);
        model.addAttribute("mainViewData", boardService.getModelOfTopByMain(BoardType.FREE.name(), 3));
        return "index";
    }

    @GetMapping("/report")
    public String showArticlesOfReport(
            @PageableDefault(
                    size = 15,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            @ModelAttribute BoardSearchRequest request,
            @Login SessionValue sessionValue,
            Model model
    ) {
        addModelAttributeOfUserInfo(sessionValue, model);
        addModelAttributesOfBoardList(
                BoardType.REPORT.name(),
                request,
                sessionValue.getUserId(),
                model,
                pageable
        );
        return "board/articles";
    }

    @GetMapping("/free")
    public String showArticlesOfFree(
            @PageableDefault(
                    size = 15,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            @ModelAttribute BoardSearchRequest request,
            @Login SessionValue sessionValue,
            Model model
    ) {
        addModelAttributeOfUserInfo(sessionValue, model);
        addModelAttributesOfBoardList(BoardType.FREE.name(), request, sessionValue.getUserId(), model, pageable);
        return "board/articles";
    }

    @GetMapping("/new/{boardType}")
    public String showArticle(
            @PathVariable("boardType") String type,
            @Login SessionValue sessionValue,
            Model model
    ) {
        User user = userService.findUserById(sessionValue.getUserId());
        String boardType = BoardType.valueOf(type.toUpperCase()).name();
        List<CategoryResponse> categoryResponses = categoryService.findCategoriesByBoardType(boardType);
        BoardResponse boardResponse = BoardResponse.builder()
                .boardType(boardType)
                .userId(user.getId())
                .build();

        model.addAttribute("userName", user.getName());
        model.addAttribute("categories", categoryResponses);
        model.addAttribute("article", boardResponse);
        return "board/newArticle";
    }

    public void addModelAttributesOfBoardList(
            String boardType,
            BoardSearchRequest request,
            Integer userId,
            Model model,
            Pageable pageable
    ) {
        if (BoardType.FREE.name().equals(boardType)) {
            model.addAttribute("grades", getGrades());
        }
        List<CategoryResponse> categoryResponses = categoryService.findCategoriesByBoardType(boardType);
        model.addAttribute("categories", categoryResponses);

        Page<BoardResponse> boardResponses = boardService.findPageByBoardType(boardType, pageable, request, userId);
        model.addAttribute("articles", boardResponses);
        model.addAttribute("boardType", boardType);
        model.addAttribute("selectedType", request.getSelectedCategories());
    }

    public void addModelAttributeOfUserInfo(
            SessionValue sessionValue,
            Model model
    ) {
        User user = userService.findUserById(sessionValue.getUserId());
        model.addAttribute("userName", user.getName());
    }
}
