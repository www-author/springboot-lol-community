package com.lol.community.comment.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.dto.response.BoardMainResponse;
import com.lol.community.board.service.BoardService;
import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentPageRequestDTO;
import com.lol.community.comment.dto.CommentPageResponseDTO;
import com.lol.community.comment.dto.CommentRequestDTO;
import com.lol.community.comment.dto.CommentResponseDTO;
import com.lol.community.comment.repository.CommentRepository;
import com.lol.community.reaction.service.LikeReactionService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService{

  private final CommentRepository commentRepository;

  @Transactional(readOnly = true)
  @Override
  public Comment getComment(Integer comment_id){
    return commentRepository.findById(comment_id).orElseThrow(()->new NoSuchElementException("없는 댓글입니다."));
  }

  @Transactional(readOnly = true)
  @Override
  public CommentPageResponseDTO getCommentList(Integer board_id, int page, Integer user_id) {

    CommentPageRequestDTO commentPageRequestDTO = CommentPageRequestDTO.builder()
        .board_id(board_id)
        .page(page)
        .size(15) // 한 페이지에 들어가는 댓글 개수
        .build();

    Page<CommentResponseDTO> commentList = commentRepository.findAllByBoardId(board_id, commentPageRequestDTO.getPageable(
        Sort.by("co_order").ascending()));

    return new CommentPageResponseDTO(commentList, user_id);
  }

  @Transactional
  @Override
  public void writeComment(CommentRequestDTO commentRequestDTO) {

    Integer board_id = commentRequestDTO.getBoard_id();
    Integer parent_id = commentRequestDTO.getParent_id();

    Comment comment = commentRequestDTO.dtoToEntity();

    if(parent_id != null){
      Comment parent = commentRepository.findCommentById(parent_id);
      if(parent == null){
        throw new NoSuchElementException("부모 댓글이 없습니다.");
      }else{
        comment.updateParent(parent);
        comment.updateDepth(parent.getCo_depth()+1);
        comment.updateOrder(parent.getCo_order()+1);
        commentRepository.pushBackOrder(board_id, parent.getCo_order());
      }
    }else{
      comment.updateOrder(commentRepository.getMaxOrder(board_id));
      comment.updateParent(null);
    }

    commentRepository.save(comment);
  }

  @Transactional
  @Override
  public void updateComment(CommentRequestDTO commentRequestDTO) {
    Comment comment = commentRepository.findById(commentRequestDTO.getId()).orElseThrow(()-> new NoSuchElementException("없는 댓글입니다."));
    comment.updateContent(commentRequestDTO.getContent());
//    commentRepository.save(comment);
  }

  @Transactional
  @Override
  public boolean deleteComment(Integer comment_id) {

    Comment comment = commentRepository.findCommentById(comment_id);

    boolean isDelete = false;

    if(comment.getChildren().size() > 0){
        comment.changeIsDelete(true);
        isDelete = true;
    }else{
      commentRepository.delete(checkDeletableParentComment(comment));
      isDelete = true;
    }

    return isDelete;
  }

  @Override
  public List<CommentResponseDTO> getBestComment(Integer board_id){

    List<CommentResponseDTO> result = commentRepository.findBestComment(board_id);

    log.info(result);
    if(result == null){
      return null;
    }else{
      return result;
    }
  }

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

  private Comment checkDeletableParentComment(Comment comment){
    Comment parent = comment.getParent();
    if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == true){
      return checkDeletableParentComment(parent);
    }
    return comment;
  }
}
