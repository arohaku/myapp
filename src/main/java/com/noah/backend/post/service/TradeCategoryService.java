package com.noah.backend.post.service;

import com.noah.backend.post.exception.CategoryNotFoundException;
import com.noah.backend.post.domain.entity.Category;
import com.noah.backend.post.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import static com.noah.backend.commons.config.CacheKeyConfig.*;

@Service
@RequiredArgsConstructor
public class TradeCategoryService implements CategoryService{

    private final CategoryRepository categoryRepository;


    @Override
    @Cacheable(key = "#categoryName", value = CATEGORY, cacheManager = "redisCacheManager", cacheNames = CATEGORY)
    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName)
                .orElseThrow (() -> new CategoryNotFoundException(categoryName));
    }
}