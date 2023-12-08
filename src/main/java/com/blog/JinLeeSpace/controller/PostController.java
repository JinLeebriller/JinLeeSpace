package com.blog.JinLeeSpace.controller;

import com.blog.JinLeeSpace.dto.PostFormDto;
import com.blog.JinLeeSpace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 포스트 작성 페이지로 이동
    @GetMapping("/post/new")
    public String postForm(Model model) {
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/postForm";
    }

}
