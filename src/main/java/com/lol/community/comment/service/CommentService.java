package com.lol.community.comment.service;

import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentPageResponseDTO;
import com.lol.community.comment.dto.CommentRequestDTO;
import com.lol.community.comment.dto.CommentResponseDTO;
import com.lol.community.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentService {

  Comment getComment(Integer comment_id);

  CommentPageResponseDTO getCommentList(Integer board_id, int page, Integer user_id);

  void writeComment(CommentRequestDTO commentRequestDTO);

  void updateComment(CommentRequestDTO commentRequestDTO);

  boolean deleteComment(Integer comment_id);

  List<CommentResponseDTO> getBestComment(Integer board_id);

  default List<BoardMainResponse> findAllCommentByGroupByWithBoard(String boardType, Integer limit) {
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
