package com.lol.community.reaction.service;

//import com.lol.community.board.domain.Board;
import com.lol.community.board.service.BoardService;
import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.service.CommentService;
//import com.lol.community.reaction.domain.LikeReactionBoard;
import com.lol.community.reaction.domain.LikeReactionComment;
//import com.lol.community.reaction.dto.LikeReactionBoardDTO;
import com.lol.community.reaction.dto.LikeReactionCommentDTO;
//import com.lol.community.reaction.repository.LikeReactionBoardRepository;
import com.lol.community.reaction.repository.LikeReactionCommentRepository;
import com.lol.community.user.domain.User;
import com.lol.community.user.login.SessionValue;
import com.lol.community.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class LikeReactionServiceImpl implements LikeReactionService {

  private final LikeReactionCommentRepository likeReactionCommentRepository;
//  private final LikeReactionBoardRepository likeReactionBoardRepository;
  private final BoardService boardService;
  private final CommentService commentService;
  private final UserService userService;

  @Override
  public boolean checkCommentIsLike(Integer user_id, Integer comment_id){
    if(likeReactionCommentRepository.findByUserIdAndCommentId(user_id, comment_id) == null){
      return false;
    }
    return true;
  }

//  @Override
//  public boolean checkBoardIsLike(Integer user_id, Integer board_id){
//    if(likeReactionBoardRepository.findByUserAndBoard(user_id, board_id) == null){
//      return false;
//    }
//    return true;
//  }

  @Transactional
  @Override
  public Long addLikeComment(LikeReactionCommentDTO likeReactionCommentDTO) {
    Integer user_id = likeReactionCommentDTO.getUser_id();
    Integer comment_id = likeReactionCommentDTO.getComment_id();

    User user = userService.findUserById(user_id);
    Comment comment = commentService.getComment(comment_id);
    LikeReactionComment likeReactionComment = likeReactionCommentRepository.findByUserIdAndCommentId(user_id, comment_id);

    comment.updateLikeCount(1L);
    likeReactionCommentRepository.save(likeReactionCommentDTO.dtoToEntity(user, comment));

    return comment.getLikeCount();
  }

  @Transactional
  @Override
  public Long subLikeComment(LikeReactionCommentDTO likeReactionCommentDTO) {
    Integer user_id = likeReactionCommentDTO.getUser_id();
    Integer comment_id = likeReactionCommentDTO.getComment_id();

    User user = userService.findUserById(user_id);

    Comment comment = commentService.getComment(comment_id);
    LikeReactionComment likeReactionComment = likeReactionCommentRepository.findByUserIdAndCommentId(user_id, comment_id);

    comment.updateLikeCount(-1L);
    likeReactionCommentRepository.deleteById(likeReactionComment.getCrId());

    return comment.getLikeCount();
  }

//  @Transactional
//  @Override
//  public Integer changeLikeBoard(LikeReactionBoardDTO likeReactionBoardDTO) {
//    Integer user_id = likeReactionBoardDTO.getUser_id();
//    Integer board_id = likeReactionBoardDTO.getBoard_id();
//
//    User user = userService.findUserById(user_id);
//    Board board = boardService.findById(board_id);
//    LikeReactionBoard likeReactionBoard = likeReactionBoardRepository.findByUserAndBoard(user_id, board_id);
//
//    if(likeReactionBoard != null){
//      board.updateLikeCount(-1);
//      likeReactionBoardRepository.deleteById(likeReactionBoard.getId());
//    }else{
//      board.updateLikeCount(1);
//      likeReactionBoardRepository.save(likeReactionBoardDTO.dtoToEntity(user, board));
//    }
//
//    return board.getLikeCount();
//  }
}
