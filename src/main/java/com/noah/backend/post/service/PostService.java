package com.noah.backend.post.service;

import com.noah.backend.post.dto.PostRequest;
import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.domain.entity.Post;

public interface PostService {

    public void createNewPost(PostRequest postRequest, Member member);

    public Post findPostById(Long postId);

    public boolean updatePost(Post post, PostRequest postRequest);

    public boolean removePost(Post post);

    public boolean isMatchedAuthor(Post post);
}
