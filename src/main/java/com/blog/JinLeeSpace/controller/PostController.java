package com.blog.JinLeeSpace.controller;

import com.blog.JinLeeSpace.dto.PostFormDto;
import com.blog.JinLeeSpace.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

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

    // 포스트 작성 이후
    @PostMapping("/post/new")
    public String postForm(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model, @RequestParam("postImgFile") List<MultipartFile> postImgFileList, Principal principal) {

        // 포스트 등록 시 필수 값이 없다면 다시 포스트 등록 페이지로 전환
        if(bindingResult.hasErrors()) {
            return "post/postForm";
        }

        // 포스트 등록 시 첫 번째 이미지(대표 이미지)가 없다면 에러 페이지와 함께 포스트 등록 페이지로 전환
        if(postImgFileList.get(0).isEmpty() && postFormDto.getIdNumber() == null) {
            model.addAttribute("errorMessage", "대표 이미지는 필수 입력 값입니다.");
            return "post/postForm";
        }

        // 포스트 저장 로직을 호출
        try {
            postService.savePost(principal.getName(), postFormDto, postImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "포스트 등록 중 에러가 발생하였습니다.");
            return "post/postForm";
        }

        return "redirect:/";
    }

}
