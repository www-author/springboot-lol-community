package com.lol.community.board.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.domain.BoardType;
import com.lol.community.board.dto.BoardSearch;
import com.lol.community.board.dto.request.BoardRequest;
import com.lol.community.board.dto.request.BoardSearchRequest;
import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.board.dto.response.BoardResponse;
import com.lol.community.board.repository.BoardRepository;
import com.lol.community.comment.service.CommentService;
import com.lol.community.user.domain.Grade;
import com.lol.community.user.domain.User;
import com.lol.community.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.lol.community.global.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final BoardReactionService boardReactionService;

    @Override
    public Board save(
            BoardRequest request
    ) {
        User user = userService.findUserById(request.getUserId());
        Board board = request.toEntityByUser(user);
        return boardRepository.save(board);
    }

    @Override
    public Board findById(Integer id) {
        return boardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_ARTICLE.getMessage() + "(id : " + id + ")"));
    }

    @Override
    @Transactional
    public Board modify(
            Integer id,
            BoardRequest request
    ) {
        Board board = findById(id);
        board.update(request);
        return board;
    }

    @Override
    public void deleteById(Integer id) {
        Board board = findById(id);
        boardRepository.delete(board);
    }

    @Override
    public Page<BoardResponse> findPageByBoardType(
            String boardType,
            Pageable pageable,
            BoardSearchRequest request,
            Integer userId
    ) {
        BoardSearch boardSearch = BoardSearch.of(
                boardType,
                pageable,
                request,
                Page.empty(pageable),
                new ArrayList<>()
        );
        if (boardType.equals(BoardType.REPORT.name())) {
            return searchReportBoard(boardSearch);
        } else {
            return searchFreeBoard(boardSearch, userId);
        }
    }

    private Page<BoardResponse> searchReportBoard(BoardSearch boardSearch) {
        BoardSearchRequest request = boardSearch.getSearchRequest();
        if (request.isEmptyCategory() && request.isEmptyKeyword()) {
            return getBoardPage(boardSearch);
        }

        if (request.isEmptyCategory()) {
            return getBoardPageWithTitle(boardSearch);
        } else if (request.isEmptyKeyword()) {
            return getBoardPageWithCategoryId(boardSearch);
        }
        return getBoardPageWithCategoryIdAndTitle(boardSearch);
    }

    private Page<BoardResponse> searchFreeBoard(BoardSearch boardSearch, Integer userId) {
        BoardSearchRequest request = boardSearch.getSearchRequest();
        Pageable pageable = boardSearch.getPageable();
        String grade = userService.findUserById(userId).getGrade();
        Integer gradeId = Grade.valueOf(grade).getId();
        List<String> accessGrades = Grade.findAllAccessGradeById(gradeId);

        boolean isDenyAccess = accessGrades.isEmpty();
        if (request.isExistGrade()) {
            isDenyAccess = !gradeId.equals(request.getGrades());
        }

        if (isDenyAccess) {
            List<BoardResponse> exceptionResponses = new ArrayList<>(
                    Collections.singleton(BoardResponse.builder()
                            .message(NOT_ACCESS_BOARD.getMessage())
                            .build()));
            return new PageImpl<>(exceptionResponses, pageable, 0);
        }

        Page<Board> boardPage = boardRepository.findBoardsByBoardTypeWithGrades(
                boardSearch.getBoardType(),
                accessGrades,
                pageable
        );

        if (boardPage.isEmpty()) {
            List<BoardResponse> exceptionResponses = new ArrayList<>(
                    Collections.singleton(BoardResponse.builder()
                            .message(NOT_FOUND_ARTICLES_OF_GRADE.getMessage())
                            .build()));
            return new PageImpl<>(exceptionResponses, pageable, 0);
        }
        
        if (request.isEmptyCategory() && request.isEmptyKeyword()) {
            List<BoardResponse> responses = boardPage.stream()
                    .map(BoardResponse::new)
                    .toList();
            return new PageImpl<>(responses, pageable, 0);
        }

        if (!request.isEmptyCategory() && !request.isEmptyKeyword()) {
            List<BoardResponse> responses = boardPage.stream()
                    .filter(page -> page.getCategory().equals(request.getCategories())
                            && request.getKeyword().toLowerCase().contains(page.getTitle().toLowerCase()))
                    .map(BoardResponse::new)
                    .toList();
            return new PageImpl<>(responses, pageable,boardPage.getTotalElements());
        }

        if (request.isEmptyCategory()) {
            List<BoardResponse> responses = boardPage.stream()
                    .filter(page -> request.getKeyword().toLowerCase().contains(page.getTitle().toLowerCase()))
                    .map(BoardResponse::new)
                    .toList();
            return new PageImpl<>(responses, pageable,boardPage.getTotalElements());
        }

        List<BoardResponse> responses = boardPage.stream()
                .filter(page -> page.getCategory().equals(request.getCategories()))
                .map(BoardResponse::new)
                .toList();
        return new PageImpl<>(responses, pageable,boardPage.getTotalElements());
    }

    private Page<BoardResponse> getBoardPageWithCategoryIdAndTitle(BoardSearch boardSearch) {
        Page<Board> page = findAllByCategoryIdAndTitle(
                boardSearch.getBoardType(),
                boardSearch.getSearchRequest().getCategories(),
                boardSearch.getSearchRequest().getKeyword(),
                boardSearch.getPageable()
        );
        List<BoardResponse> responses = page.stream()
                .map(BoardResponse::new)
                .toList();
        return new PageImpl<>(
                responses,
                boardSearch.getPageable(),
                page.getTotalElements()
        );
    }

    private Page<BoardResponse> getBoardPageWithCategoryId(BoardSearch boardSearch) {
        Page<Board> page = findAllByCategoryId(
                boardSearch.getBoardType(),
                boardSearch.getSearchRequest().getCategories(),
                boardSearch.getPageable()
        );
        List<BoardResponse> responses = page.stream()
                .map(BoardResponse::new)
                .toList();
        return new PageImpl<>(
                responses,
                boardSearch.getPageable(),
                page.getTotalElements()
        );
    }

    private Page<BoardResponse> getBoardPageWithTitle(BoardSearch boardSearch) {
        Page<Board> page = findAllByTitle(
                boardSearch.getBoardType(),
                boardSearch.getSearchRequest().getKeyword(),
                boardSearch.getPageable()
        );
        List<BoardResponse> responses = page.stream()
                .map(BoardResponse::new)
                .toList();
        return new PageImpl<>(
                responses,
                boardSearch.getPageable(),
                page.getTotalElements()
        );
    }

    private Page<BoardResponse> getBoardPage(BoardSearch boardSearch) {
        Page<Board> page = findAll(
                boardSearch.getBoardType(),
                boardSearch.getPageable()
        );
        List<BoardResponse> responses = page.stream()
                .map(BoardResponse::new)
                .toList();
        return new PageImpl<>(
                responses,
                boardSearch.getPageable(),
                page.getTotalElements()
        );
    }


    @Override
    public Page<Board> findAllByCategoryIdAndTitle(
            String boardType,
            Integer categoryId,
            String keyword,
            Pageable pageable
    ) {
        return boardRepository.findAllByBoardTypeAndIsDeletedIsFalseAndCategoryIdAndTitleContainingIgnoreCase(
                boardType,
                categoryId,
                keyword,
                pageable
        );
    }

    @Override
    public Page<Board> findAllByCategoryId(
            String boardType,
            Integer category,
            Pageable pageable
    ) {
        return boardRepository.findAllByBoardTypeAndCategoryIdAndIsDeletedIsFalse(
                boardType,
                category,
                pageable
        );
    }

    @Override
    public Page<Board> findAllByTitle(
            String boardType,
            String keyword,
            Pageable pageable

    ) {
        return boardRepository.findAllByBoardTypeAndTitleContainingIgnoreCaseAndIsDeletedIsFalse(
                boardType,
                keyword,
                pageable
        );
    }

    @Override
    public Page<Board> findAll(
            String boardType,
            Pageable pageable
    ) {
        return boardRepository.findAllByBoardTypeAndIsDeletedIsFalse(
                boardType,
                pageable
        );
    }

    @Override
    public Map<String, List<BoardMainResponse>> getModelOfTopByMain(
            String boardType,
            Integer limit
    ) {
        Map<String, List<BoardMainResponse>> mainModelAttributes = new HashMap<>();
        mainModelAttributes.put("orderByViewCount", findTopByViewCount(boardType, limit));
        mainModelAttributes.put("orderByCommentCount", findTopByCommentCount(boardType, limit));
        mainModelAttributes.put("orderByLikeCount", findTopByLikeCount(boardType, limit));
        return mainModelAttributes;
    }


    // 조회 수
    @Override
    public List<BoardMainResponse> findTopByViewCount(
            String boardType,
            Integer limit
    ) {
        return boardRepository.findAllByBoardTypeAndIsDeletedIsFalseOrderByViewCountDesc(
                boardType,
                PageRequest.of(0, limit)
        )
                .stream()
                .map(BoardMainResponse::new)
                .toList();
    }

    // 추천 수
    @Override
    public List<BoardMainResponse> findTopByLikeCount(
            String boardType,
            Integer limit
    ) {
        return boardReactionService.findAllReactionGroupByWithBoard(boardType, limit);
    }

    // 댓글 수
    @Override
    public List<BoardMainResponse> findTopByCommentCount(
            String boardType,
            Integer limit
    ) {
        return commentService.findAllCommentByGroupByWithBoard(boardType, limit);
    }
}
