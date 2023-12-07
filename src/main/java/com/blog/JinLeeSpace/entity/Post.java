package com.blog.JinLeeSpace.entity;

// 포스트 정보를 저장하는 Post 엔티티

import com.blog.JinLeeSpace.dto.PostFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
public class Post extends BaseEntity {

    // 포스트별 고유 번호
    @Id
    @Column(name = "post_idNumber")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idNumber")
    private Member member;

    // 포스트 제목
    @Column(nullable = false, length = 50)
    private String title;

    // 포스트 내용
    @Lob
    @Column(nullable = false)
    private String content;

    // 조회수
    private int viewCount;

    // 포스트 수정 메서드
    public void modifyPost(PostFormDto postFormDto) {
        this.title = postFormDto.getTitle();
        this.content = postFormDto.getContent();
    }

    // 조회수를 증가시키는 메서드
    public void addViewCount(int viewCount) {
        this.viewCount += viewCount;
    }
}
