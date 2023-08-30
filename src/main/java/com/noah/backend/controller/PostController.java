package com.noah.backend.controller;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.domain.dto.PostRequest;
import com.noah.backend.domain.dto.PostResponse;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.domain.entity.Post;
import com.noah.backend.interceptor.LoginMember;
import com.noah.backend.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.noah.backend.commons.HttpStatusResponseEntity.RESPONSE_OK;
import static com.noah.backend.commons.HttpStatusResponseEntity.RESPONSE_UNAUTHORIZED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @LoginRequired
    @PostMapping
    public ResponseEntity<HttpStatus> createNewPost(@RequestBody @Valid PostRequest postCreateRequest,
                                                    @LoginMember Member member) {

        postService.createNewPost(postCreateRequest, member);

        return RESPONSE_OK;
    }

    @LoginRequired
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long postId) {

        return ResponseEntity.ok(PostResponse.of(postService.findPostById(postId)));
    }

    @LoginRequired
    @PutMapping("/{postId}")
    public ResponseEntity<HttpStatus> updatePost(@Valid @RequestBody PostRequest postRequest,
                                                 @PathVariable Long postId) {

        Post post = postService.findPostById(postId);

        if(postService.updatePost(post, postRequest)) {
            return RESPONSE_OK;
        }
        return RESPONSE_UNAUTHORIZED;

    }
}
