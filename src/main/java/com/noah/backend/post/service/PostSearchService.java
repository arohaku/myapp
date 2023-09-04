package com.noah.backend.post.service;

import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.dto.PostPageResponse;
import org.springframework.data.domain.Pageable;

public interface PostSearchService {

    public PostPageResponse findAllByMemberAddress(Member member, Pageable pageable);
}
