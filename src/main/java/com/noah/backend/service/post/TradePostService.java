package com.noah.backend.service.post;

import com.noah.backend.commons.advice.exception.CategoryNotFoundException;
import com.noah.backend.commons.annotation.AreaInfoRequired;
import com.noah.backend.domain.dto.PostCreateRequest;
import com.noah.backend.domain.entity.Category;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.domain.entity.Post;
import com.noah.backend.domain.repository.post.CategoryRepository;
import com.noah.backend.domain.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService implements PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    @Override
    @AreaInfoRequired
    @Transactional
    public void createNewPost(PostCreateRequest postCreateRequest, Member member) {
        Post post = postCreateRequest.toEntity(member);

        Category category = categoryRepository.findCategoryByCategoryName(
                postCreateRequest.getCategory()).orElseThrow (() -> new CategoryNotFoundException(postCreateRequest.getCategory()));

        post.setCategory(category);

        postRepository.save(post);
    }
}