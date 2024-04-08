package com.lol.community.reaction.repository;

import com.lol.community.reaction.domain.LikeReactionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeReactionCommentRepository extends JpaRepository<LikeReactionComment, Integer> {

//  LikeReactionComment findByUserIdAndCommentId(Integer user_id, Integer comment_id);

  LikeReactionComment findByUserIdAndCommentId(Integer user_id, Integer comment_id);
}
