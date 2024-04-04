package com.lol.community.board.dto.response;

public interface BoardMainView {
    Long getTotalCount();
    Integer  getBoardId();
    String getWriter();
    String getTitle();
    String getContent();
}
