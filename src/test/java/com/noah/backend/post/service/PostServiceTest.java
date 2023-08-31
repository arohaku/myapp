package com.noah.backend.post.service;

import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.member.exception.UnAuthorizedAccessException;
import com.noah.backend.member.service.LoginService;
import com.noah.backend.post.domain.entity.Category;
import com.noah.backend.post.domain.entity.Post;
import com.noah.backend.post.domain.repository.PostRepository;
import com.noah.backend.post.dto.PostRequest;
import com.noah.backend.post.exception.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private TradePostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private LoginService loginService;

    private Member member;

    private Post post;

    private Category category;

    private PostRequest postRequest;

    @BeforeEach
    void setUp() {

        postRequest = PostRequest.builder()
                .title("test")
                .content("test content")
                .category("디지털/가전")
                .build();

        member = Member.builder()
                .email("noah@admin.com")
                .password("1q2w3e4r!")
                .nickname("하쿠애비")
                .build();

        post = postRequest.toEntity(member);
    }

    @Test
    @DisplayName("게시글이 성공적으로 등록될 경우 PostRepository.save(Post post), " +
            "CategoryService.findCategoryByName(String category) 메서드가 한번씩 호출된다.")
    void successToCreatePost() {
        // given
        when(categoryService.findCategoryByName(any())).thenReturn(category);

        // when
        postService.createNewPost(postRequest, member);

        // then
        verify(postRepository, times(1)).save(any(Post.class));
        verify(categoryService, times(1)).findCategoryByName(anyString());
    }

    @Test
    @DisplayName("해당 아이디의 게시글이 존재하지 않으면 PostNotFoundException 예외를 발생시킨다.")
    void isNotExistPostFindById() {
        // given
        when(postRepository.findPostById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(PostNotFoundException.class, () -> {
            Post findByPostId = postService.findPostById(1L);
        });
    }

    @Test
    @DisplayName("해당 아이디의 게시글이 존재하는 경우 정상적으로 게시글을 조회한다.")
    void isExistPostFindById() {
        // given
        when(postRepository.findPostById(any())).thenReturn(Optional.of(post));

        // when
        Post findByPostId = postService.findPostById(post.getId());


        // then
        assertThat(findByPostId).isNotNull();
        assertThat(findByPostId.getId()).isEqualTo(post.getId());
        assertThat(findByPostId.getTitle()).isEqualTo(post.getTitle());
        assertThat(findByPostId.getContent()).isEqualTo(post.getContent());
    }

    @Test
    @DisplayName("게시글이 성공적으로 업데이트 되는 경우 Post.updatePost(PostRequest request), " +
            "Post.setCategory(Category category), CategoryService.findCategoryByName(String category) 메서드가 한번씩 호출된다.")
    void successToUpdatePost() {
        // given
        Post post = mock(Post.class);
        when(post.getAuthor()).thenReturn(member);
        when(categoryService.findCategoryByName(any())).thenReturn(category);
        when(loginService.getLoginMember()).thenReturn(member);

        // when
        postService.updatePost(post, postRequest);

        // then
        verify(post, times(1)).updatePost(postRequest);
        verify(post, times(1)).setCategory(category);
    }

    @Test
    @DisplayName("작성자가 일치하지 않을 경우 게시글 업데이트가 실패하고 UnAuthorizedAccessException이 발생한다.")
    void isUnAuthorizedMemberToUpdatePost() {
        // given
        Member member = mock(Member.class);
        when(loginService.getLoginMember()).thenReturn(member);

        // then
        assertThrows(UnAuthorizedAccessException.class, () -> {
            postService.updatePost(post, postRequest);
        });
    }

    @Test
    @DisplayName("작성자가 일치할 경우 게시글 삭제에 성공하고 게시글의 removed가 true로 변경된다.")
    void successToRemovePost() {
        // given
        Post post = mock(Post.class);
        when(post.getRemoved()).thenReturn(true);

        // when
        postService.removePost(post);

        // then
        assertTrue(post.getRemoved());
    }

    @Test
    @DisplayName("작성자가 일치하지 않을 경우 게시글이 삭제에 실패하고 UnAuthroizedAccessException이 발생한다.")
    void isUnAuthorizedMemberToRemovePost() {
        // given
        Member member = mock(Member.class);
        when(loginService.getLoginMember()).thenReturn(member);

        // then
        assertThrows(UnAuthorizedAccessException.class, () -> {
            postService.removePost(post);
        });
    }





}