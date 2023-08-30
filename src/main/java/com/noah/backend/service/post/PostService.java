package com.noah.backend.service.post;

import com.noah.backend.domain.dto.PostRequest;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.domain.entity.Post;

public interface PostService {

    public void createNewPost(PostRequest postRequest, Member member);

    public Post findPostById(Long postId);

    public boolean updatePost(Post post, PostRequest postRequest);
}
