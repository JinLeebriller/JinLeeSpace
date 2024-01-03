package com.blog.JinLeeSpace.controller;

import com.blog.JinLeeSpace.dto.MainPostDto;
import com.blog.JinLeeSpace.dto.PostSearchDto;
import com.blog.JinLeeSpace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;

    @GetMapping("/")
    public String main(PostSearchDto postSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<MainPostDto> postList = postService.getMainPostPage(postSearchDto, pageable);
        model.addAttribute("postList", postList);
        model.addAttribute("postSearchDto", postSearchDto);
        model.addAttribute("maxPage", 5);
        return "main";
    }

}
