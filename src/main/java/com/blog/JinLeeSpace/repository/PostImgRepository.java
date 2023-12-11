package com.blog.JinLeeSpace.repository;

import com.blog.JinLeeSpace.entity.Post;
import com.blog.JinLeeSpace.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {

    Optional<PostImg> findByIdNumber(Long idNumber);

    List<PostImg> findByPostIdNumberOrderByIdNumberAsc(Long IdNumber);

}
