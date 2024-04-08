package com.lol.community.comment.repository;

import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentResponseDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommentRepository {

  Page<CommentResponseDTO> findAllByBoardId(Integer board_id, Pageable pageable);

  List<CommentResponseDTO> findBestComment(Integer board_id);
}
