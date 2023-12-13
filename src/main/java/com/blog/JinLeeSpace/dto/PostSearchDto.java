package com.blog.JinLeeSpace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchDto {

    /*
        현재 시간과 포스트 등록일을 비교해서 포스트 데이터를 조회
        - all : 포스트 등록일 전체
        - 1d : 최근 하루 동안 등록된 포스트
        - 1w : 최근 일주일 동안 등록된 포스트
        - 1m : 최근 한달 동안 등록된 포스트
        - 6m : 최근 6개월 동안 등록된 포스트
    */
    private String searchDateType;

    /*
        포스트를 조회할 때 어떤 유형으로 조회할지 선택
        - title : 포스트 제목
        - content : 포스트 내용
        - createdBy : 포스트 등록자 아이디
    */
    private String searchBy;

    // 조회할 검색어를 저장할 변수(searchBy가 postTitle일 경우 제목을 기준으로 검색하고, createdBy일 경우 등록자 아이디 기준으로 검색)
    private String searchQuery;

}
