package com.lol.community.freeBoard.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardDTO {

  private Long id;
  private String title;
  private String content;
  @Setter
  private Long writerId;
  private String writerName;
  private String category;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
  private Long replyCount;

}
