package com.blog.JinLeeSpace.service;

import com.blog.JinLeeSpace.dto.PostFormDto;
import com.blog.JinLeeSpace.entity.Member;
import com.blog.JinLeeSpace.entity.Post;
import com.blog.JinLeeSpace.entity.PostImg;
import com.blog.JinLeeSpace.repository.MemberRepository;
import com.blog.JinLeeSpace.repository.PostImgRepository;
import com.blog.JinLeeSpace.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

// 포스트를 등록하는 클래스
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImgService postImgService;
    private final PostImgRepository postImgRepository;
    private final MemberRepository memberRepository;

    public Long savePost(Principal principal, PostFormDto postFormDto, List<MultipartFile> postImgFileList) throws Exception {

        // 포스트 등록
        Post post = postFormDto.createPost();
        Member member = memberRepository.findById(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(principal.getName()));
        post.setMember(member);
        postRepository.save(post);

        // 이미지 등록
        for(int i = 0 ; i < postImgFileList.size() ; i++) {
            PostImg postImg = new PostImg();
            postImg.setPost(post);
            if(i == 0) {
                postImg.setRepimgYn("Y");
            } else {
                postImg.setRepimgYn("N");
            }
            postImgService.savePostImg(postImg, postImgFileList.get(i));
        }

        return post.getIdNumber();
    }

}
