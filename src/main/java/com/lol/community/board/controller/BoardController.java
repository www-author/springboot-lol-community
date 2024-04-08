package com.lol.community.board.controller;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.request.BoardSearchRequest;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.service.BoardReactionService;
import com.lol.community.board.service.BoardService;
import com.lol.community.category.dto.response.CategoryResponse;
import com.lol.community.category.service.CategoryService;
import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentResponseDTO;
import com.lol.community.comment.service.CommentService;
import com.lol.community.user.domain.User;
import com.lol.community.user.login.Login;
import com.lol.community.user.login.SessionValue;
import com.lol.community.user.service.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final BoardReactionService boardReactionService;

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
        addModelAttributesOfBoardList(BoardType.REPORT.name(), request, model, pageable);
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
        addModelAttributesOfBoardList(BoardType.FREE.name(), request, model, pageable);
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


    @GetMapping("/{board_id}")
    public String getArticle(@PathVariable Integer board_id, @Login SessionValue sessionValue, Model model) {
        User user = userService.findUserById(sessionValue.getUserId());
        if(user == null){
            return "redirect:/login";
        }

        Board board = boardService.findById(board_id);
        if(board == null) {
            throw new NoSuchElementException("존재하지 않는 글입니다.");
        }

        List<CommentResponseDTO> bestCommentList = commentService.getBestComment(board.getId());

        boolean isLike = boardReactionService.checkIsLikeArticle(board_id, user.getId());

        model.addAttribute("bestCommentList", bestCommentList);
        model.addAttribute("user_id", user.getId());
        model.addAttribute("user_name", user.getName());
        model.addAttribute("commentResult", commentService.getCommentList(board.getId(), 1, user.getId()));
        model.addAttribute("article", board);
        model.addAttribute("articleIsLike", isLike);

        System.out.println(board);

        return "board/article";
    }

    public void addModelAttributesOfBoardList(
            String boardType,
            BoardSearchRequest request,
            Model model,
            Pageable pageable
    ) {
        if (BoardType.FREE.name().equals(boardType)) {
            model.addAttribute("grades", getGrades());
        }
        List<CategoryResponse> categoryResponses = categoryService.findCategoriesByBoardType(boardType);
        model.addAttribute("categories", categoryResponses);
        model.addAttribute("articles", boardService.findPageByBoardType(boardType, pageable, request));
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
