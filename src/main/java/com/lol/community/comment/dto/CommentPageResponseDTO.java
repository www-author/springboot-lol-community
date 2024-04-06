package com.lol.community.comment.dto;

import com.lol.community.comment.domain.Comment;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentPageResponseDTO {

  private List<CommentResponseDTO> dtoList;
  private int totalPage;
  private int page;
  private int size;
  private int start, end;
  private boolean prev, next;
  private List<Integer> pageList;

  public CommentPageResponseDTO(Page<Comment> result){
   dtoList = result.stream().map(e -> CommentResponseDTO.entityToDTO(e)).toList();
   totalPage = result.getTotalPages();
   makePageList(result.getPageable());
  }

  private void makePageList(Pageable pageable){
    this.page = pageable.getPageNumber() + 1;
    this.size = pageable.getPageSize();

    int tempEnd = (int)(Math.ceil(page/5.0))*5;

    start = tempEnd - 4;

    prev = start > 1;

    end = totalPage > tempEnd ? tempEnd : totalPage;

    next = totalPage > tempEnd;

    pageList = IntStream.rangeClosed(start, end).boxed().toList();
  }
}
