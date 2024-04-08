package com.lol.community.comment.repository;

import static com.lol.community.comment.domain.QComment.comment;
import static com.lol.community.reaction.domain.QLikeReactionComment.likeReactionComment;
import static com.lol.community.user.domain.QUser.user;

import com.lol.community.comment.domain.Comment;
import com.lol.community.comment.dto.CommentResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<CommentResponseDTO> findAllByBoardId(Integer board_id, Pageable pageable){
//    return jpaQueryFactory.selectFrom(comment)
//        .leftJoin(comment.parent)
//        .fetchJoin()
//        .where(comment.board.id.eq(board_id))
//        .orderBy(
//            comment.parent.id.asc().nullsFirst(),
//            comment.createdAt.asc()
//        ).fetch();
//    this.id = id;
//    this.userName = userName;
//    this.content = content;
//    this.co_depth = co_depth;
//    this.co_order = co_order;
//    this.create_at = create_at;
//    this.updated_at = updated_at;
//    this.like_id = like_id;
//    this.likeCount = likeCount;
    List<CommentResponseDTO> test = jpaQueryFactory.select(Projections.constructor(
        CommentResponseDTO.class,
        comment.id, user.name, user.id, comment.content, comment.co_depth, comment.co_order, comment.createdAt, comment.updatedAt,
        likeReactionComment.user.id, comment.likeCount))
        .from(comment)
        .leftJoin(likeReactionComment)
        .on(comment.id.eq(likeReactionComment.comment.id))
        .leftJoin(user).on(comment.user.id.eq(user.id))
        .where(comment.board.id.eq(board_id))
        .orderBy(
            comment.co_order.asc(),
            comment.co_depth.asc()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

//    List<Comment> result = jpaQueryFactory.selectFrom(comment)
//        .leftJoin(likeReactionComment)
//        .fetchJoin()
//        .where(comment.board.id.eq(board_id))
//        .orderBy(
//            comment.co_order.asc(),
//            comment.co_depth.asc()
//        )
//        .offset(pageable.getOffset())
//        .limit(pageable.getPageSize())
//        .fetch();

    JPQLQuery<Comment> count = jpaQueryFactory.select(comment)
        .from(comment)
        .where(comment.board.id.eq(board_id));

    return PageableExecutionUtils.getPage(test, pageable, count::fetchCount);
  }

  @Override
  public List<CommentResponseDTO> findBestComment(Integer board_id){
    return jpaQueryFactory.select(Projections.constructor(CommentResponseDTO.class,
            comment.id,
            comment.user.name,
            comment.content,
            comment.co_depth,
            comment.co_order,
            comment.createdAt,
            comment.updatedAt,
            comment.likeCount
            ))
        .from(comment)
        .where(comment.board.id.eq(board_id)
            .and(comment.likeCount
                .eq(JPAExpressions.select(comment.likeCount.max()).from(comment)
        )).and(comment.likeCount.goe(5))).orderBy(comment.likeCount.asc()).limit(2).fetch();
  }
}
