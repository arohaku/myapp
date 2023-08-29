package com.noah.backend.service.post;

import com.noah.backend.domain.dto.PostCreateRequest;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.domain.entity.Post;
import com.noah.backend.domain.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public void createNewPost(PostCreateRequest postCreateRequest, Member member) {
        Post post = postCreateRequest.toEntity(member);
        postRepository.save(post);
    }
}