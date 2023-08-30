package com.noah.backend.post.service;

import com.noah.backend.post.exception.PostNotFoundException;
import com.noah.backend.commons.annotation.AreaInfoRequired;
import com.noah.backend.post.dto.PostRequest;
import com.noah.backend.post.domain.entity.Category;
import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.domain.entity.Post;
import com.noah.backend.post.domain.repository.PostRepository;
import com.noah.backend.member.service.LoginService;
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

        if (isMatchedAuthor(post)) {
            Category category = categoryService.findCategoryByName(postRequest.getCategory());

            post.updatePost(postRequest);
            post.setCategory(category);

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean removePost(Post post) {

        if(isMatchedAuthor(post)) {
            post.removePost();
            return true;
        }

        return false;
    }

    @Override
    public boolean isMatchedAuthor(Post post) {


        Member member = loginService.getLoginMember();

        if(post.getAuthor() != member) {
            return false;
        }

        return true;
    }
}