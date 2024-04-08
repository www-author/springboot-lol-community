package com.lol.community.reaction.service;

//import com.lol.community.reaction.dto.LikeReactionBoardDTO;
import com.lol.community.reaction.dto.LikeReactionCommentDTO;

public interface LikeReactionService {

  boolean checkCommentIsLike(Integer user_id, Integer comment_id);

//  boolean checkBoardIsLike(Integer user_id, Integer board_id);

  Long addLikeComment(LikeReactionCommentDTO likeReactionCommentDTO);
  Long subLikeComment(LikeReactionCommentDTO likeReactionCommentDTO);

//  Integer changeLikeBoard(LikeReactionBoardDTO likeReactionBoardDTO);
}
