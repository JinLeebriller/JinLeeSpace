package com.blog.JinLeeSpace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 포스팅별 이미지 정보를 저장하는 PostImg 엔티티

@Entity
@Table(name = "post_img")
@Getter @Setter
public class PostImg extends BaseEntity{

    // 포스트 이미지별 고유 번호
    @Id
    @Column(name="postImg_idNumber")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idNumber;

    // 이미지 파일명
    private String imgName;

    // 원본 이미지 파일명
    private String oriImgName;

    // 이미지 조회 경로
    private String imgUrl;

    // 대표 이미지 여부
    private String repimgYn;

    // Post 엔티티와 다대일 단방향 관계로 매핑
    // 지연 로딩을 설정하여 매핑된 Post 엔티티가 피요할 경우 데이터를 조회
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idNumber")
    private Post post;

    // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력 받아서 이미지 정보를 업데이트하는 메서드
    public void updatePostImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}
