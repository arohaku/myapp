package com.noah.backend.post.controller;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.commons.interceptor.LoginMember;
import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.dto.PostPageResponse;
import com.noah.backend.post.service.TradePostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/searches")
public class PostSearchController {

    private final TradePostSearchService tradePostSearchService;

    @LoginRequired
    @GetMapping
    public ResponseEntity<PostPageResponse> getTradePosts(@LoginMember Member member, Pageable pageable) {

        PostPageResponse page = tradePostSearchService.findAllByMemberAddress(member, pageable);

        return ResponseEntity.ok(page);
    }
}