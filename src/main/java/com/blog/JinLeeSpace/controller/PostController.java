package com.blog.JinLeeSpace.controller;

import com.blog.JinLeeSpace.dto.PostFormDto;
import com.blog.JinLeeSpace.dto.PostSearchDto;
import com.blog.JinLeeSpace.entity.Post;
import com.blog.JinLeeSpace.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
        if (bindingResult.hasErrors()) {
            return "post/postForm";
        }

        // 포스트 등록 시 첫 번째 이미지(대표 이미지)가 없다면 에러 페이지와 함께 포스트 등록 페이지로 전환
        if (postImgFileList.get(0).isEmpty() && postFormDto.getIdNumber() == null) {
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

    // 포스트 수정 페이지로 이동
    @GetMapping("/post/modify/{idNumber}")
    public String postDtl(@PathVariable("idNumber") Long idNumber, Model model) {

        try {
            PostFormDto postFormDto = postService.getPostDtl(idNumber);
            model.addAttribute("postFormDto", postFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 포스트입니다.");
            model.addAttribute("postFormDto", new PostFormDto());
            return "post/postForm";
        }

        return "post/postForm";
    }

    // 포스트 수정 후
    @PostMapping("/post/modify/{idNumber}")
    public String postUpdate(@Valid PostFormDto postFormDto, BindingResult bindingResult, @RequestParam("postImgFile") List<MultipartFile> postImgFileList, Model model) {

        if (bindingResult.hasErrors()) {
            return "post/postForm";
        }

        if (postImgFileList.get(0).isEmpty() && postFormDto.getIdNumber() == null) {
            model.addAttribute("errorMessage", "첫 번째 포스트 이미지는 필수 입력 값입니다.");
            return "post/postForm";
        }

        try {
            postService.updatePost(postFormDto, postImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "포스트 수정 중 에러가 발생하였습니다.");
            return "post/postForm";
        }

        return "redirect:/";
    }

    // 조회한 포스트를 화면에 전달하는 로직
    @GetMapping({"/post/list", "/post/list/{page}"})
    public String postList(PostSearchDto postSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        // 페이징을 위해서 PageRequest.of 메서드를 통해 Pageable 객체를 생성
        // 첫 번째 파라미터 : 조회할 페이지 번호, 두 번째 파라미터 : 한 번에 가지고 올 데이터 수
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Post> postList = postService.getPostPage(postSearchDto, pageable);
        // 조회한 포스트 데이터 및 페이징 정보를 뷰에 전달
        model.addAttribute("postList", postList);
        // 페이지 전환 시 기존 검색 조건을 유지한 채 이동할 수 있도록 뷰에 다시 전달
        model.addAttribute("postSearchDto", postSearchDto);
        // 포스트 메뉴 하단에 보여줄 페이지 번호의 최대 개수
        model.addAttribute("maxPage", 5);

        return "post/postList";
    }

}
