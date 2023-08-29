package com.noah.backend.domain.dto;

import com.noah.backend.domain.entity.Category;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.domain.entity.Post;
import com.noah.backend.domain.entity.TradeStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
public class PostCreateRequest {

    @NotEmpty
    @Length(max = 100, message = "제목은 최대 100글자를 넘을 수 없습니다.")
    private final String title;

    @NotEmpty
    private final String content;

    @NotEmpty
    private final String category;

    public Post toEntity(Member member) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .category(Category.of(this.category))
                .author(member)
                .address(member.getAddress())
                .location(member.getLocation())
                .status(TradeStatus.SALE)
                .build();
    }
}
