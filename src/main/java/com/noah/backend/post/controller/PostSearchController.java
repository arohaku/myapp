package com.noah.backend.post.controller;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.commons.interceptor.LoginMember;
import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.dto.AddressRequest;
import com.noah.backend.post.dto.PostPageResponse;
import com.noah.backend.post.service.TradePostSearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @LoginRequired
    @GetMapping("/address")
    public ResponseEntity<PostPageResponse> getTradePostsByAddress(@Valid AddressRequest address, Pageable pageable) {

        PostPageResponse page = tradePostSearchService.findAllByAddress(address, pageable);

        return ResponseEntity.ok(page);
    }

    @LoginRequired
    @GetMapping("/categories")
    public ResponseEntity<PostPageResponse> getTradePostsByCategory(@RequestParam("category") @NotEmpty String category,
                                                                    @LoginMember Member member, Pageable pageable) {

        PostPageResponse page = tradePostSearchService.findAllByCategory(category, member, pageable);

        return ResponseEntity.ok(page);
    }
}