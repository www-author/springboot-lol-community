package com.lol.community.comment.repository;

import static com.lol.community.comment.domain.QComment.*;

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
  public Page<Comment> findAllByBoardId(Long board_id, Pageable pageable){
//    return jpaQueryFactory.selectFrom(comment)
//        .leftJoin(comment.parent)
//        .fetchJoin()
//        .where(comment.board.id.eq(board_id))
//        .orderBy(
//            comment.parent.id.asc().nullsFirst(),
//            comment.createdAt.asc()
//        ).fetch();

    List<Comment> result = jpaQueryFactory.selectFrom(comment)
        .where(comment.board.id.eq(board_id))
        .orderBy(
            comment.co_order.asc(),
            comment.co_depth.asc()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPQLQuery<Comment> count = jpaQueryFactory.select(comment)
        .from(comment)
        .where(comment.board.id.eq(board_id));

    return PageableExecutionUtils.getPage(result, pageable, count::fetchCount);
  }

  @Override
  public List<CommentResponseDTO> findBestComment(Long board_id){
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
        )).and(comment.likeCount.goe(1))).orderBy(comment.likeCount.asc()).limit(2).fetch();
  }
}
