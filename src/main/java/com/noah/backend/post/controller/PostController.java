package com.noah.backend.post.controller;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.post.dto.PostRequest;
import com.noah.backend.post.dto.PostResponse;
import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.domain.entity.Post;
import com.noah.backend.commons.interceptor.LoginMember;
import com.noah.backend.post.service.PostService;
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

        postService.updatePost(post, postRequest);

        return RESPONSE_OK;

    }


    @LoginRequired
    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {

        Post post = postService.findPostById(postId);

        postService.removePost(post);

        return RESPONSE_OK;
    }
}
