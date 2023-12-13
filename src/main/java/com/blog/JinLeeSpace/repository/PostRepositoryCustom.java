package com.blog.JinLeeSpace.repository;

import com.blog.JinLeeSpace.dto.PostSearchDto;
import com.blog.JinLeeSpace.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable);

}
