package com.noah.backend.domain.repository.post;

import com.noah.backend.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findCategoryByCategoryName(String categoryName);
}
