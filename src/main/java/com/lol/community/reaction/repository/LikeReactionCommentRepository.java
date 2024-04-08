package com.lol.community.reaction.repository;

import com.lol.community.comment.domain.Comment;
import com.lol.community.reaction.domain.LikeReactionComment;
import com.lol.community.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeReactionCommentRepository extends JpaRepository<LikeReactionComment, Integer> {

//  LikeReactionComment findByUserIdAndCommentId(Integer user_id, Integer comment_id);

  LikeReactionComment findByUserIdAndCommentId(Integer user_id, Integer comment_id);
}
