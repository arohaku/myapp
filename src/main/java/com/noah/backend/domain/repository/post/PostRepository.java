package com.noah.backend.domain.repository.post;

import com.noah.backend.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    public Optional<Post> findPostById(Long postId);
}