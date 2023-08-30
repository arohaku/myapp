package com.noah.backend.service.post;

import com.noah.backend.commons.advice.exception.CategoryNotFoundException;
import com.noah.backend.commons.advice.exception.PostNotFoundException;
import com.noah.backend.commons.annotation.AreaInfoRequired;
import com.noah.backend.domain.dto.PostRequest;
import com.noah.backend.domain.entity.Category;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.domain.entity.Post;
import com.noah.backend.domain.repository.post.CategoryRepository;
import com.noah.backend.domain.repository.post.PostRepository;
import com.noah.backend.service.member.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService implements PostService {

    private final PostRepository postRepository;

    private final CategoryService categoryService;

    private final LoginService loginService;

    @Override
    @AreaInfoRequired
    @Transactional
    public void createNewPost(PostRequest postRequest, Member member) {

        Post post = postRequest.toEntity(member);

        Category category = categoryService.findCategoryByName(postRequest.getCategory());

        post.setCategory(category);

        postRepository.save(post);
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findPostById(postId).orElseThrow(PostNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean updatePost(Post post, PostRequest postRequest) {

        Member member = loginService.getLoginMember();

        if(post.getAuthor() != member) {
            return false;
        }
        Category category = categoryService.findCategoryByName(postRequest.getCategory());

        post.updatePost(postRequest);
        post.setCategory(category);
        return true;
    }
}