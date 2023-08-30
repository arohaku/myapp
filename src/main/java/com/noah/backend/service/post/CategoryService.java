package com.noah.backend.service.post;

import com.noah.backend.domain.entity.Category;

public interface CategoryService {

    public Category findCategoryByName(String categoryName);
}
