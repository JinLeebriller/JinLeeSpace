package com.blog.JinLeeSpace.repository;

import com.blog.JinLeeSpace.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdNumber(Long idNumber);

}
