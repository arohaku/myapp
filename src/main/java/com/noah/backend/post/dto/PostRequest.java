package com.noah.backend.post.dto;

import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.post.domain.entity.Post;
import com.noah.backend.post.domain.entity.TradeStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotEmpty
    @Length(max = 100, message = "제목은 최대 100글자를 넘을 수 없습니다.")
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String category;

    public Post toEntity(Member member) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(member)
                .address(member.getAddress())
                .location(member.getLocation())
                .status(TradeStatus.SALE)
                .build();
    }
}
