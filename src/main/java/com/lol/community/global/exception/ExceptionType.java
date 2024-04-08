package com.lol.community.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    NOT_EXIST_USER("해당 사용자가 존재하지 않습니다."),
    NOT_EXIST_ARTICLE("해당 게시글이 존재하지 않습니다."),
    NOT_ACCESS_BOARD("게시글 목록을 조회할 수 있는 권한이 없습니다."),
    NOT_FOUND_ARTICLES_OF_GRADE("해당 등급에 대한 게시글 목록이 존재하지 않습니다."),
    NOT_ACCESS_ARTICLE("해당 게시글을 조회할 수 있는 권한이 없습니다.");

    private final String message;
}
