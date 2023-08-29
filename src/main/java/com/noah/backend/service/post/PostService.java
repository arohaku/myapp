package com.noah.backend.service.post;

import com.noah.backend.domain.dto.PostCreateRequest;
import com.noah.backend.domain.entity.Member;

public interface PostService {

    public void createNewPost(PostCreateRequest postCreateRequest, Member member);
}
