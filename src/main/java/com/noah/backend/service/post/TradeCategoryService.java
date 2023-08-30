package com.noah.backend.service.post;

import com.noah.backend.commons.advice.exception.CategoryNotFoundException;
import com.noah.backend.domain.entity.Category;
import com.noah.backend.domain.repository.post.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.noah.backend.commons.CacheKey.CATEGORY;

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