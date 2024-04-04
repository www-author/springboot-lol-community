package com.lol.community.board.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BoardSearchRequest {
    private Integer categories;
    private Integer grades;
    private String keyword;

    public BoardSearchRequest() {
        this.categories = ObjectUtils.defaultIfNull(categories, 0);
        this.grades = ObjectUtils.defaultIfNull(grades, 0);
    }

    public boolean isEmptyCategory() {
        return categories == 0;
    }

    public boolean isEmptyGrade() {
        return grades == 0;
    }

    public boolean isEmptyKeyword() {
        return Strings.isBlank(keyword);
    }

    public Map<String, Integer> getSelectedCategories() {
        Map<String, Integer> selectedCategories = new HashMap<>();
        selectedCategories.put("category", categories);
        selectedCategories.put("grade", grades);
        return selectedCategories;
    }
}
