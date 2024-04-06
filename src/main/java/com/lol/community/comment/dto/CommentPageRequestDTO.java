package com.lol.community.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
public class CommentPageRequestDTO {

  private Long board_id;
  private int page;
  private int size;

  public CommentPageRequestDTO(){
    page = 1;
    size = 15;
  }

  public Pageable getPageable(Sort sort){
    return PageRequest.of(page - 1, size, sort);
  }
}
