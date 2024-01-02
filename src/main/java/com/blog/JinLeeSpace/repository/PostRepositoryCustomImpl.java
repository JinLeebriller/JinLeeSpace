package com.blog.JinLeeSpace.repository;

import com.blog.JinLeeSpace.dto.MainPostDto;
import com.blog.JinLeeSpace.dto.PostSearchDto;
import com.blog.JinLeeSpace.entity.Post;
import com.blog.JinLeeSpace.entity.QPost;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    // 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용
    private JPAQueryFactory queryFactory;

    // JPAQueryFactory의 생성자로 EntityManager 객체를 넣어준다.
    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
        포스트 목록 페이지(postList.html) 포스트 목록 검색 조건
    */

    // searchDateType의 값에 따라서 dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간 이후로 등록된 상품만 조회
    // 예를 들어, searchDateType 값이 "1m"인 경우 dateTime의 시간을 한 달 전으로 세팅 후 최근 한 달 동안 등록된 상품을 조회하도록 조건값을 반환
    private BooleanExpression regDateAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QPost.post.regTime.after(dateTime);
    }

    // searchBy의 값에 따라서 제목 또는 내용에 검색어를 포함하고 있는 포스트,
    // 포스트 작성자의 아이디에 검색어를 포함하고 있는 포스트를 조회하도록 조건값을 반환
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if(StringUtils.equals("title", searchBy)) {
            return QPost.post.title.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("content", searchBy)) {
            return QPost.post.content.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QPost.post.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        QueryResults<Post> results = queryFactory
                .selectFrom(QPost.post)
                .where(regDateAfter(postSearchDto.getSearchDateType()),
                        searchByLike(postSearchDto.getSearchBy(), postSearchDto.getSearchQuery()))
                .orderBy(QPost.post.idNumber.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    /*
        메인 페이지 포스트 목록 검색 조건
    */

    // 검색어가 null이 아니면 포스트명에 검색어가 포함되는 포스트를 조회하는 조건 반환
    private BooleanExpression postTilteLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QPost.post.title.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainPostDto> getMainPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        return null;
    }
}
