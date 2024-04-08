package com.lol.community.board.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardReactionDTO {
  private Integer boardId;
  private Integer userId;
}
