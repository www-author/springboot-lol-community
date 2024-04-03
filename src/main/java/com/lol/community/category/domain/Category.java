package com.lol.community.category.domain;

import com.lol.community.category.dto.response.CategoryResponse;
import com.lol.community.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    @Builder
    public Category(Integer id) {
        this.id = id;
    }

    public CategoryResponse toResponse() {
        return CategoryResponse.builder()
                .id(id)
                .name(categoryName)
                .build();
    }
}
