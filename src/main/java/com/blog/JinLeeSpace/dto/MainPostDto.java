package com.blog.JinLeeSpace.dto;

import com.blog.JinLeeSpace.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainPostDto {

    // 포스트별 고유 번호
    private Long idNumber;

    // 포스트 제목
    private String title;

    // 포스트 내용
    private String content;

    // 조회수
    private Integer viewCount;

    // 이미지 조회 경로
    private String imgUrl;

    /*
        생성자에 @QueryProjection 어노테이션을 선언하여
        Querydsl로 결과 조회 시 MainPostDto 객체로 바로 받아오도록 활용
    */
    @QueryProjection
    public MainPostDto(Long idNumber, String title, String content, Integer viewCount, String imgUrl) {
        this.idNumber = idNumber;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.imgUrl = imgUrl;
    }


}
