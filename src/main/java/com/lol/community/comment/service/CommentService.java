package com.lol.community.comment.service;

import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentPageResponseDTO;
import com.lol.community.comment.dto.CommentRequestDTO;
import com.lol.community.comment.dto.CommentResponseDTO;
import java.util.List;

public interface CommentService {

  Comment getComment(Long comment_id);

  CommentPageResponseDTO getCommentList(Long board_id, int page);

  void writeComment(CommentRequestDTO commentRequestDTO);

  void updateComment(CommentRequestDTO commentRequestDTO);

  boolean deleteComment(Long comment_id);

  List<CommentResponseDTO> getBestComment(Long board_id);

}
