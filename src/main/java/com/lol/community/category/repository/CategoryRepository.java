package com.lol.community.category.repository;

import com.lol.community.category.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query("select child from Category child join fetch child.parent where child.parent.categoryName = :categoryName")
    List<Category> findChildrenByParentCategoryName(@Param("categoryName") String categoryName);
}
