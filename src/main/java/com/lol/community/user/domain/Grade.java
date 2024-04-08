package com.lol.community.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Grade {
    SUPER_ADMIN(1, "최고 관리자"),
    ADMIN(2, "일반 관리자"),
    EXPERT(3,"고수"),
    INTERMEDIATE(4, "중수"),
    BEGINNER(5, "하수");

    private final Integer id;
    private final String name;

    public static List<Grade> getGrades() {
        return Arrays.stream(Grade.class.getEnumConstants())
                .filter(value -> !value.name().contains("ADMIN"))
                .toList();

    }

    public static List<String> findAllAccessGradeById(Integer gradeId) {
            return Arrays.stream(Grade.class.getEnumConstants())
                    .filter(grade -> grade.id <= gradeId)
                    .map(Enum::name)
                    .toList();
    }
}
