package com.blog.JinLeeSpace.repository;

import com.blog.JinLeeSpace.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {

    List<PostImg> findByPostIdNumberOrderByIdNumberAsc(Long postIdNumber);

}
