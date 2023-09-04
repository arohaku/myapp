package com.noah.backend.post.service;

import com.noah.backend.commons.annotation.AreaInfoRequired;
import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.domain.entity.Address;
import com.noah.backend.post.domain.entity.Post;
import com.noah.backend.post.domain.repository.PostSearchRepository;
import com.noah.backend.post.dto.AddressRequest;
import com.noah.backend.post.dto.PostPageResponse;
import com.noah.backend.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradePostSearchService implements PostSearchService {

    private final PostSearchRepository postSearchRepository;

    @Override
    @AreaInfoRequired
    public PostPageResponse findAllByMemberAddress(Member member, Pageable pageable) {

        Address address = member.getAddress();
        Page<Post> posts = postSearchRepository.findAllByMemberAddress(address.getState(), address.getCity(), address.getTown(), pageable);

        return getPostPageResponse(posts, pageable);
    }

    @Override
    public PostPageResponse findAllByAddress(AddressRequest address, Pageable pageable) {

        Page<Post> posts = postSearchRepository.findAllByMemberAddress(address.getState(), address.getCity(), address.getTown(), pageable);

        return getPostPageResponse(posts, pageable);
    }

        @Override
        @AreaInfoRequired
        public PostPageResponse findAllByCategory(String category, Member member, Pageable pageable) {

            Address address = member.getAddress();
            Page<Post> posts = postSearchRepository.findAllByCategory(category, address.getState(), address.getCity(), address.getTown(), pageable);

            return getPostPageResponse(posts, pageable);
        }

    private PostPageResponse getPostPageResponse(Page<Post> posts, Pageable pageable) {

        List<PostResponse> postResponses = posts.getContent().stream().map(PostResponse::of).collect(Collectors.toList());

        return PostPageResponse.builder()
                .totalPage(posts.getTotalPages())
                .currentPage(pageable.getPageNumber())
                .postResponses(postResponses)
                .build();
    }
}
