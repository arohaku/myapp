package com.noah.backend.controller;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.domain.dto.PostCreateRequest;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.interceptor.LoginMember;
import com.noah.backend.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.noah.backend.commons.HttpStatusResponseEntity.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @LoginRequired
    @PostMapping
    public ResponseEntity<HttpStatus> createNewPost(@RequestBody @Valid PostCreateRequest postCreateRequest,
                                                    @LoginMember Member member) {

        postService.createNewPost(postCreateRequest, member);

        return RESPONSE_OK;
    }
}
