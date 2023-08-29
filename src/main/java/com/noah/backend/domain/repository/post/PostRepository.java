package com.noah.backend.domain.repository.post;

import com.noah.backend.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}