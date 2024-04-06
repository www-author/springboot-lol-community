package com.lol.community.reaction.service;

import com.lol.community.reaction.dto.LikeReactionBoardDTO;
import com.lol.community.reaction.dto.LikeReactionCommentDTO;

public interface LikeReactionService {

  Long changeLikeComment(LikeReactionCommentDTO likeReactionCommentDTO);

  Long changeLikeBoard(LikeReactionBoardDTO likeReactionBoardDTO);
}
