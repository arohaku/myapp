package com.noah.backend.post.service;

import com.noah.backend.member.exception.UnAuthorizedAccessException;
import com.noah.backend.post.exception.PostNotFoundException;
import com.noah.backend.commons.annotation.AreaInfoRequired;
import com.noah.backend.post.dto.PostRequest;
import com.noah.backend.post.domain.entity.Category;
import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.domain.entity.Post;
import com.noah.backend.post.domain.repository.PostRepository;
import com.noah.backend.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.noah.backend.commons.config.CacheKeyConfig.POST;

@Service
@RequiredArgsConstructor
public class TradePostService implements PostService {

    private final PostRepository postRepository;

    private final CategoryService categoryService;

    private final LoginService loginService;

    @Override
    @AreaInfoRequired
    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    key = "#member.getAddress().state + '.' + #member.getAddress().city + '.' + #member.getAddress().town",
                    value = POST
            ),
            @CacheEvict(
                    key = "#member.getAddress().state + '.' + #member.getAddress().city + '.' + #member.getAddress().town + '.' + #postRequest.category",
                    value = POST
            )
    })
    public void createNewPost(PostRequest postRequest, Member member) {

        Post post = postRequest.toEntity(member);

        Category category = categoryService.findCategoryByName(postRequest.getCategory());

        post.setCategory(category);

        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPostById(Long postId) {
        return postRepository.findPostById(postId).orElseThrow(PostNotFoundException::new);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    key = "#member.getAddress().state + '.' + #member.getAddress().city + '.' + #member.getAddress().town",
                    value = POST
            ),
            @CacheEvict(
                    key = "#member.getAddress().state + '.' + #member.getAddress().city + '.' + #member.getAddress().town + '.' + #postRequest.category",
                    value = POST
            )
    })
    public void updatePost(Post post, PostRequest postRequest) {

        if (isMatchedAuthor(post)) {
            Category category = categoryService.findCategoryByName(postRequest.getCategory());

            post.updatePost(postRequest);
            post.setCategory(category);

        }

    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    key = "#member.getAddress().state + '.' + #member.getAddress().city + '.' + #member.getAddress().town",
                    value = POST
            ),
            @CacheEvict(
                    key = "#member.getAddress().state + '.' + #member.getAddress().city + '.' + #member.getAddress().town + '.' + #postRequest.category",
                    value = POST
            )
    })
    public void removePost(Post post) {

        if (isMatchedAuthor(post)) {
            post.removePost();
        }

    }

    @Override
    public boolean isMatchedAuthor(Post post) {


        Member member = loginService.getLoginMember();

        if (post.getAuthor() != member) {
            throw new UnAuthorizedAccessException();
        }

        return true;
    }
}