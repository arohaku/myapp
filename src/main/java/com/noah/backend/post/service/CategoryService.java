package com.noah.backend.post.service;

import com.noah.backend.post.domain.entity.Category;

public interface CategoryService {

    public Category findCategoryByName(String categoryName);
}
