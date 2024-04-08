package com.lol.community.comment.service;

import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<BoardMainResponse> findAllCommentByGroupByWithBoard(String boardType, Integer limit) {
        return commentRepository.findGroupByCommentOfBoard(boardType, PageRequest.of(0, limit))
                .stream()
                .map(view -> BoardMainResponse.builder()
                        .boardId(view.getBoardId())
                        .title(view.getTitle())
                        .content(view.getContent())
                        .writer(view.getWriter())
                        .totalCount(view.getTotalCount())
                        .build())
                .toList();
    }
}
