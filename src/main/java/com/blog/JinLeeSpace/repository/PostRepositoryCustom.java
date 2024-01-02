package com.blog.JinLeeSpace.repository;

import com.blog.JinLeeSpace.dto.MainPostDto;
import com.blog.JinLeeSpace.dto.PostSearchDto;
import com.blog.JinLeeSpace.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    /*
        포스트 조회 조건을 담고 있는 postSearchDto 객체와
        페이징 정보를 담고 있는 pageable 객체를 파라미터로 받는 getPostPage 메서드를 정의
        반환 데이터로 Page<Post> 객체를 반환

        포스트 목록 페이지(postList.html)의 페이징된 포스트 목록을 반환
    */
    Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable);

    // 메인 페이지에 보여줄 포스트 목록을 가져오는 메서드
    Page<MainPostDto> getMainPostPage(PostSearchDto postSearchDto, Pageable pageable);

}
