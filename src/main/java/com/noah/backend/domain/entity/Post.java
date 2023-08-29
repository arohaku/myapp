package com.noah.backend.domain.entity;

import com.noah.backend.commons.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;


@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member author;

    @Lob
    private String content;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    @Builder
    public Post(String title, Category category, TradeStatus status, Member author,
                String content, Address address, Location location) {
        this.title = title;
        this.category = category;
        this.status = status;
        this.author = author;
        this.content = content;
        this.address = address;
        this.location = location;
    }

}