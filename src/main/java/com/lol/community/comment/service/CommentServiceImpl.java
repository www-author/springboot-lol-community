package com.lol.community.comment.service;

import com.lol.community.board.domain.Board;
import com.lol.community.board.service.BoardService;
import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentPageRequestDTO;
import com.lol.community.comment.dto.CommentPageResponseDTO;
import com.lol.community.comment.dto.CommentRequestDTO;
import com.lol.community.comment.dto.CommentResponseDTO;
import com.lol.community.comment.repository.CommentRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService{

  private final CommentRepository commentRepository;
  private final BoardService boardService;

  @Transactional(readOnly = true)
  @Override
  public Comment getComment(Long comment_id){
    return commentRepository.findById(comment_id).orElseThrow(()->new NoSuchElementException("없는 댓글입니다."));
  }

  @Transactional(readOnly = true)
  @Override
  public CommentPageResponseDTO getCommentList(Long board_id, int page) {
    CommentPageRequestDTO commentPageRequestDTO = CommentPageRequestDTO.builder()
        .board_id(board_id)
        .page(page)
        .size(15) // 한 페이지에 들어가는 객체 개수
        .build();

    System.out.println(commentPageRequestDTO);

    Page<Comment> commentList = commentRepository.findAllByBoardId(board_id, commentPageRequestDTO.getPageable(
        Sort.by("co_order").ascending()));

//    List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
//
//    commentList.forEach(comment -> {
//      if(comment.getParent() == null){
//        CommentResponseDTO commentResponseDTO = CommentResponseDTO.entityToDTO(comment);
//        commentResponseDTOList.add(commentResponseDTO);
//      }
//    });

    return new CommentPageResponseDTO(commentList);
  }

  @Transactional
  @Override
  public void writeComment(CommentRequestDTO commentRequestDTO) {

    Board board = boardService.findById(commentRequestDTO.getBoard_id());
    if(board == null){
      throw new NoSuchElementException("존재하지 않는 글에는 댓글을 작성할 수 없습니다.");
    }

    Long board_id = commentRequestDTO.getBoard_id();
    Long parent_id = commentRequestDTO.getParent_id();

    Comment comment = commentRequestDTO.dtoToEntity();

    if(parent_id != null){
      Comment parent = commentRepository.findById(parent_id).orElse(null);
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
  public boolean deleteComment(Long comment_id) {

    Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new NoSuchElementException("없는 댓글입니다."));

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
  public List<CommentResponseDTO> getBestComment(Long board_id){

    List<CommentResponseDTO> result = commentRepository.findBestComment(board_id);

    log.info(result);
    if(result == null){
      return null;
    }else{
      return result;
    }
  }

  private Comment checkDeletableParentComment(Comment comment){
    Comment parent = comment.getParent();
    if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == true){
      return checkDeletableParentComment(parent);
    }
    return comment;
  }
}
